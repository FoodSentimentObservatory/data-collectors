package ac.uk.abdn.foobs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import ac.uk.abdn.foobs.db.DAO;
import ac.uk.abdn.foobs.db.entity.AgentEntity;
import ac.uk.abdn.foobs.db.entity.GeoPointEntity;
import ac.uk.abdn.foobs.db.entity.LocationEntity;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.db.entity.PremisesEntity;
import ac.uk.abdn.foobs.db.entity.SearchDetailsEntity;
import ac.uk.abdn.foobs.db.entity.UserAccountEntity;
import ac.uk.abdn.foobs.fsa.RatingsHandler;
import ac.uk.abdn.foobs.twitter.app.AppRESTAPI;
import ac.uk.abdn.foobs.twitter.app.SearchObject;
import ac.uk.abdn.foobs.twitter.user.TwitterHandleFinder;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;
import ac.uk.abdn.foobs.utils.TwitterKeywordSplit;
import twitter4j.GeoLocation;
import twitter4j.Query.Unit;
import twitter4j.Status;

public class TaskManager {

	public static void manageTasks(Config config) {

		if (config.getParseAndUploadRatings() == 1) {
			System.out.println("Will parse and upload ratings.");
			parseAndUploadRatings(config);
		}

		if (config.getFindTwitterAccounts() == 1) {
			System.out.println("Finding twitter handles");
			findTwitterAccounts(config);
		}

		if (config.getFindTweetsFromRestaurants() == 1) {
			System.out.println("Finding 200 tweets by each restaurant");
			findTweetsFromRestaurants(config);
		}

		if (config.getFindTweetsContainingKeywords() == 1){
			System.out.println("Finding 200 tweets containing keywords");
			findTweetsContainingKeywords(config);
		}
	}

	private static void parseAndUploadRatings(Config config) {
		File ratingsFile = new File(config.getRatingFile());

		UserRESTAPI restAPI = new UserRESTAPI(config);
		ArrayList<Establishment> establishmentList = RatingsHandler.parseXml(ratingsFile, restAPI);
		System.out.println("Parsing done, uploading to database.");
		for (int i = 0; i < establishmentList.size(); i++) {
			DAO.insertEstablishment(establishmentList.get(i), config.getRatingFileCountry());
		}
	}

	private static void findTwitterAccounts(Config config) {
		Set<PremisesEntity> premisesSet = DAO.getPremisesWithUncheckedUserAccount("Twitter");

		UserRESTAPI restAPI = new UserRESTAPI(config);
		TwitterHandleFinder finder = new TwitterHandleFinder(restAPI);
		for (PremisesEntity premises : premisesSet) {
			finder.findAndInsertTwitterAccountForPremises(premises);
		}
	}

	private static void findTweetsFromRestaurants(Config config) {
		Set<UserAccountEntity> users = DAO.getTwitterAccounts();

		AppRESTAPI restAPI = new AppRESTAPI(config);
		for (UserAccountEntity user : users) {
			System.out.println("Getting tweets for: " + user.getPlatformAccountId());
			Set<Status> tweets = restAPI.showTweetsByUser(user.getPlatformAccountId(), 200);
			for (Status tweet : tweets) {
				DAO.saveTweet(user, tweet);
			}
		}
	}

	private static void findTweetsContainingKeywords(Config config) {

       

        
        ArrayList <SearchObject> searches = new ArrayList <SearchObject> ();
        
        
        
        
        //Read search details from config
        int searchCount = config.getSearchLength();
		  List<String> latitude = config.getLatitude();
		  List<String> longitude = config.getLongitude();
		  List<String> radius = config.getRadius();
		  List<String> note = config.getNote();
		  List<String> unit = config.getUnit();
	      List<List <String>> keywordFiles = config.getFileData();
	      Date startDate = new Date();
        
	      //prepare search objects for all the searches
	      for (int i=0; i<searchCount; i++) {
	    	  
	    	  String[] keywordsArray;
	          try {
	    
	                      keywordsArray = FileUtils
	                                              .readFileToString(new File(keywordFiles.get(i).get(0)), keywordFiles.get(i).get(1)).split("\n");
	          } catch (IOException e) {
	                      keywordsArray = new String[0];
	                      e.printStackTrace(System.err);
	          }
	          List<String> keywords = new ArrayList<String>();
	          
	          for (String keyword : keywordsArray) {
                  keywords.add(keyword);
	          }
	          
	      //    System.out.println("Keywords loaded from file" + keywords);
	          System.out.println("Preparing search strings for "+note.get(i) );
	          
	    	  ArrayList <String> keywrodssplit = TwitterKeywordSplit.createKeywordAndPhrasesStrings(keywords);
	    	  
	    	  System.out.println("Keyword split results: ");
	    	  
	    	  for (int j =0; j <keywrodssplit.size();j++) {
	    		  System.out.println("Keyword list number: " +(j+1) +" " + keywrodssplit.get(j)+"; length " + keywrodssplit.get(j).length());
	    	  }
	    	 
	    	  ArrayList <Long> previousIDsFromThisSearch = config.getPreviousTweetIDsForIndividualSearches().get(note.get(i));
	    			  for (int j =0; j <keywrodssplit.size();j++) {
	    		  SearchObject searchDetails = new SearchObject();
	    	        searchDetails.setKeywords(keywrodssplit.get(j));
	    	       
	    	        LocationEntity location = new LocationEntity();
	    	        GeoLocation geoLocation = new   GeoLocation(Double.parseDouble(latitude.get(i)), Double.parseDouble(longitude.get(i)));
	    	        GeoPointEntity geoPoint = new GeoPointEntity(geoLocation);
	    	        geoPoint.setLocationId(location);
	    	        location.setGeoPoint(geoPoint);
	    	        location.setDisplayString(note.get(i));	    	        
	    	        searchDetails.setRadius(Double.parseDouble(radius.get(i)));
	    	        
	    	        if (previousIDsFromThisSearch.size()>j) {
	    	        	searchDetails.setLastKonwnCachedID(previousIDsFromThisSearch.get(j));
	    	        }
	    	        
	    	        searchDetails.setLocationId(location);
	    	        searchDetails.setNote(note.get(i));
	    	        searchDetails.setStartOfSearch(startDate);
	    	        searches.add(searchDetails);
	    	   
	    	        
	    	        
	    	        DAO.saveSearchDetails((SearchDetailsEntity) searchDetails);
	    	  }
	    	  
	    	  
	      }
        
        
        
        //ignore the keywords until config is read properly
        
        
        
        PlatformEntity twitter = DAO.getPlatfromBasedOnName("Twitter");
        AppRESTAPI restAPI = new AppRESTAPI(config);
        
        // GeoLocation(56.496467, -3.801270) approx coordinates for Scotland
        // GeoLocation(53.157312, -1.362305) approx coordinates for England
        // GeoLocation (52.104256,-0.516357) approx coordinates for England including London
        // radius for all the above: 177.312
        // GeoLocation (50.700517,-3.993530) approx coordinates for Plymouth
        // radius for Plymouth: 50
        
        // This will be read from the config
     //   GeoLocation geoLocation = new   GeoLocation (52.104256,-0.516357);
       
      //  double radius = 177.312;
       
        //String temporary_keywords_for_testing = "and";
        
        //Create all searches here .. This will go into loop
        
       
        /*
        SearchObject searchDetails = new SearchObject();
        searchDetails.setKeywords(temporary_keywords_for_testing);
       
        LocationEntity location = new LocationEntity();
        GeoLocation geoLocation = new   GeoLocation(53.157312, -1.362305);
        GeoPointEntity geoPoint = new GeoPointEntity(geoLocation);
        geoPoint.setLocationId(location);
        location.setGeoPoint(geoPoint);
        location.setDisplayString("England");
        
        searchDetails.setRadius(177.312);
        
        searchDetails.setLocationId(location);
      
        
        searchDetails.setNote("England");
        
      
        
        SearchObject searchDetails2 = new SearchObject();
        searchDetails2.setKeywords(temporary_keywords_for_testing);
        
         location = new LocationEntity();
         geoLocation = new   GeoLocation(56.496467, -3.801270);
         geoPoint = new GeoPointEntity(geoLocation);
        geoPoint.setLocationId(location);
        location.setGeoPoint(geoPoint);
        location.setDisplayString("Scotland");
        
        searchDetails2.setRadius(177.312);
        
        searchDetails2.setLocationId(location);
      
        
        searchDetails2.setNote("Scotland");
        
        
        searches.add(searchDetails);
		searches.add(searchDetails2);
		
		 searchDetails.setStartOfSearch(startDate);
        searchDetails2.setStartOfSearch(startDate);
       // Set<Status> tweets = restAPI.searchKeywordListGeoCoded(keywords, 1000000, geoLocation, radius, Unit.km);
       
        DAO.saveSearchDetails((SearchDetailsEntity) searchDetails);
        DAO.saveSearchDetails((SearchDetailsEntity) searchDetails2);
		
        */
        //Loop will end here
        
        
        
        
        
        
        
        
        
        
       
        
        restAPI.searchKeywordListGeoCodedMultipleSearches(searches);
        
        
        Date endDate = new Date();
        
     // populate the following however is suitable
       
        //Looop to add the times
        
        
        for (int i =0; i< searches.size(); i++) {
        	searches.get(i).setEndOfSearch(endDate);
        	 DAO.saveSearchDetails((SearchDetailsEntity) searches.get(i));
        }
        
        /*
        searchDetails.setEndOfSearch(endDate);
        
       
        searchDetails2.setEndOfSearch(endDate);
       
        

        DAO.saveSearchDetails((SearchDetailsEntity) searchDetails);
        DAO.saveSearchDetails((SearchDetailsEntity) searchDetails2);
        
        */
        //String queryString = keywords.stream().map(Object::toString).collect(Collectors.joining("\" OR \""));
        //queryString = "\""+queryString+"\"";
        
        
       
        
        
        /// move to saveTweets
        /*
        for (Status tweet : tweets){
                    
                    
    // create a UserAccountEntity for the Status user to ensure the correct
    // platformAccountId is used as part of the DB lookup.
    UserAccountEntity basicUser = new UserAccountEntity(tweet.getUser());
                    UserAccountEntity dbUser = DAO.getUserAccountByIdAndPlatform(basicUser.getPlatformAccountId(),  twitter);
                    if (dbUser != null){
                                // already have this user in the DB
                                basicUser = dbUser;
                                // TODO: This will not overwrite the existing record of the user with any changes they have made, 
                                // details from their profile was stored in the DB
                    } else {
                                // new user to the system, so initialise it
                       basicUser.setPlatformId(twitter);
                       AgentEntity agent = new AgentEntity();
                       agent.setAgentType("Person");
                       basicUser.setAgentId(agent);
                    }
                    basicUser = DAO.saveOrUpdateUserAccount(basicUser);
                    DAO.saveTweet(basicUser, tweet, searchDetails);
        } */
        
}

}
