package ac.uk.abdn.foobs.twitter.app;

import ac.uk.abdn.foobs.Config;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class AppRESTAPI {
   private Twitter twitter;

   public AppRESTAPI(Config config) {
      try {
         ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setApplicationOnlyAuthEnabled(true);
         cb.setOAuthConsumerKey(config.getTwitterAppConsumerKey());
         cb.setOAuthConsumerSecret(config.getTwitterAppConsumerSecret());
         TwitterFactory tf = new TwitterFactory(cb.build());
         twitter = tf.getInstance();
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
