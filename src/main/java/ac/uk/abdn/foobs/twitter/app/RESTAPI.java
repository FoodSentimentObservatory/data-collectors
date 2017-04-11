package ac.uk.abdn.foobs.twitter.app;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class RESTAPI {
   private Twitter twitter;

   public RESTAPI() {
      try {
         twitter = TwitterFactory.getSingleton();
         twitter.getOAuth2Token();
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
   }

   public QueryResult search(Query query) {
      QueryResult result = null;

      try {
         result = twitter.search(query);
         System.out.println(twitter.getRateLimitStatus("search"));
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      
      return result;
   }

}
