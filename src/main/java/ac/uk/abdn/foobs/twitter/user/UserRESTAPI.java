package ac.uk.abdn.foobs.twitter.user;

import ac.uk.abdn.foobs.Config;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class UserRESTAPI {
   private Twitter twitter;

   public UserRESTAPI(Config config) {
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
      try {
         users = twitter.searchUsers(query, 20);
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      return users;
   }
}
