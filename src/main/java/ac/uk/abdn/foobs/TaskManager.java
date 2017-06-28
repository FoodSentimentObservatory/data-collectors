package ac.uk.abdn.foobs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import ac.uk.abdn.foobs.db.DAO;
import ac.uk.abdn.foobs.db.entity.AgentEntity;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.db.entity.PremisesEntity;
import ac.uk.abdn.foobs.db.entity.UserAccountEntity;
import ac.uk.abdn.foobs.fsa.RatingsHandler;
import ac.uk.abdn.foobs.twitter.app.AppRESTAPI;
import ac.uk.abdn.foobs.twitter.user.TwitterHandleFinder;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;
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
		List<String> keywords = new ArrayList<String>();

		String[] keywordsArray;
		try {
			keywordsArray = FileUtils
					.readFileToString(new File(config.getKeywordsFilename()), config.getKeywordsFileEncoding()).split("\n");
		} catch (IOException e) {
			keywordsArray = new String[0];
			e.printStackTrace(System.err);
		}
		for (String keyword : keywordsArray) {
			keywords.add(keyword);
		}
		System.out.println(keywords);
		PlatformEntity twitter = DAO.getPlatfromBasedOnName("Twitter");

		AppRESTAPI restAPI = new AppRESTAPI(config);
		Set<Status> tweets = restAPI.searchList(keywords, 1);
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
			DAO.saveOrUpdateUserAccount(basicUser);
			DAO.saveTweet(basicUser, tweet);
		}
	}
}
