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
      super(config);
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
      String resource = "/search/tweets";
      QueryResult result = null;

      try {
         if (decrementAndCheckRemaining(resource)) {
            result = twitter.search(query);
         } else {
            System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
            Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
         }
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      } catch (InterruptedException e) {
         System.out.println(e.getMessage());
      }
      
      return result;
   }

   public ResponseList<Status> showTweetsByUser(String userHandle) {
      String resource = "/statuses/user_timeline";
      ResponseList<Status> statuses = null;

      try {
         if (decrementAndCheckRemaining(resource)) {
            statuses = twitter.getUserTimeline(userHandle);
         } else {
            System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
            Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
         }
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      } catch (InterruptedException e) {
         System.out.println(e.getMessage());
      }

      return statuses;
   }

   public QueryResult searchRepliesToUser(String userHandle) {
      String resource = "/search/tweets";
      QueryResult result = null;

      Query query = new Query();
      query.setQuery("to:"+userHandle);
      try {
         if (decrementAndCheckRemaining(resource)) {
            result = twitter.search(query);
         } else {
            System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
            Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
         }
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      } catch (InterruptedException e) {
         System.out.println(e.getMessage());
      }
      
      return result;
   }

   public QueryResult searchMentionsOfUser(String userHandle) {
      String resource = "/search/tweets";
      QueryResult result = null;

      Query query = new Query();
      query.setQuery("@"+userHandle);
      try {
         if (decrementAndCheckRemaining(resource)) {
            result = twitter.search(query);
         } else {
            System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
            Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
         }
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      } catch (InterruptedException e) {
         System.out.println(e.getMessage());
      }
      
      return result;

   }
}
