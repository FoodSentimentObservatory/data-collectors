package ac.uk.abdn.foobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import ac.uk.abdn.foobs.db.DAO;
import ac.uk.abdn.foobs.db.entity.PremisesEntity;
import ac.uk.abdn.foobs.fsa.RatingsHandler;
import ac.uk.abdn.foobs.twitter.user.TwitterHandleFinder;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;

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

}
