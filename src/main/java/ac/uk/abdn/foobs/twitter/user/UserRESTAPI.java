package ac.uk.abdn.foobs.twitter.user;

import ac.uk.abdn.foobs.Config;
import ac.uk.abdn.foobs.twitter.BaseRESTAPI;

import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class UserRESTAPI extends BaseRESTAPI {

   public UserRESTAPI(Config config) {
      super(config);
   }

   protected void connectToTwitter(Config config) {
      ConfigurationBuilder cb = new ConfigurationBuilder();
      cb.setOAuthConsumerKey(config.getTwitterUserConsumerKey());
      cb.setOAuthConsumerSecret(config.getTwitterUserConsumerSecret());
      cb.setOAuthAccessToken(config.getTwitterUserAccessToken());
      cb.setOAuthAccessTokenSecret(config.getTwitterUserAccessTokenSecret());
      TwitterFactory tf = new TwitterFactory(cb.build());
      twitter = tf.getInstance();
   }

   public ResponseList<User> searchUser(String query) {
      ResponseList<User> users = null;
      String resource = "/users/search";

      try {
         if (decrementAndCheckRemaining(resource)) {
            users = twitter.searchUsers(query, -1);
         } else {
            System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
         }
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      return users;
   }
}
