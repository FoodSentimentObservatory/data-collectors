package ac.uk.abdn.foobs.twitter.app;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

public class TwitterMain {
   public static void main(String[] args) {
      connectToRESTAPI();
   }

   private static void connectToRESTAPI() {
      RESTAPI restAPI = new RESTAPI();
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
}
