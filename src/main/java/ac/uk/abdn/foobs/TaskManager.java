package ac.uk.abdn.foobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import ac.uk.abdn.foobs.db.DAO;
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
      Set<PremisesEntity> premisesSet = 
             DAO.getPremisesWithUncheckedUserAccount("Twitter");

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
         Set<Status> tweets = restAPI.showTweetsByUser(user.getPlatformAccountId(), 200);
         for (Status tweet : tweets) {
            DAO.saveTweet(user, tweet);
         }
      }
   }
}
