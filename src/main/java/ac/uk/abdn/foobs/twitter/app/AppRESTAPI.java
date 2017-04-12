package ac.uk.abdn.foobs.twitter.app;

import ac.uk.abdn.foobs.Config;
import ac.uk.abdn.foobs.twitter.BaseRESTAPI;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class AppRESTAPI extends BaseRESTAPI {

   public AppRESTAPI(Config config) {
      connectToTwitter(config);
   }

   protected void connectToTwitter(Config config) {
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
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      
      return result;
   }

   public ResponseList<Status> showTweetsByUser(String userHandle) {
      ResponseList<Status> statuses = null;

      try {
         statuses = twitter.getUserTimeline(userHandle);
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }

      return statuses;
   }

   public QueryResult searchRepliesToUser(String userHandle) {
      QueryResult result = null;

      Query query = new Query();
      query.setQuery("to:"+userHandle);
      try {
         result = twitter.search(query);
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      
      return result;
   }

   public QueryResult searchMentionsOfUser(String userHandle) {
      QueryResult result = null;

      Query query = new Query();
      query.setQuery("@"+userHandle);
      try {
         result = twitter.search(query);
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      
      return result;

   }
}
