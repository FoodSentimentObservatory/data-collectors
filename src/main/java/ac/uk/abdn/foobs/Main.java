package ac.uk.abdn.foobs;

import java.io.File;
import java.util.ArrayList;

import ac.uk.abdn.foobs.fsa.RatingsHandler;
import ac.uk.abdn.foobs.twitter.app.AppRESTAPI;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;
import ac.uk.abdn.foobs.twitter.user.TwitterHandleFinder;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

public class Main {
   public static void main(String[] args) {
      File file = new File(args[0]);
      Config config = new Config(file);

      connectToAppOnlyTwitter(config);

      File xml = new File(args[1]);
      findTwitterHanldes(config, xml);
   }

   private static void connectToAppOnlyTwitter(Config config) {
      AppRESTAPI restAPI = new AppRESTAPI(config);
      Query query = new Query("google");
      //Query query = new Query().geoCode(new GeoLocation(57.149651, -2.099075), 10, "km");
      //Query query = new Query("TechCrunch");
      QueryResult result = restAPI.search(query);
      if (result != null) {
         for (Status status : result.getTweets()) {
            System.out.println(status.getCreatedAt());
         }
      } else {
         System.out.println("returned empty");
      }
   }

   private static void findTwitterHanldes(Config config, File ratingsXML) {
      ArrayList<Establishment> establishmentList = RatingsHandler.parseXml(ratingsXML);

      UserRESTAPI restAPI = new UserRESTAPI(config);

      System.out.println("Finding twitter handles for the ratings");
      TwitterHandleFinder finder = new TwitterHandleFinder(restAPI);
      finder.findHandlesForEstablishements(ratingsXML, establishmentList);

   }
}
