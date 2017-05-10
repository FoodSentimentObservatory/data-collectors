package ac.uk.abdn.foobs.twitter.user;

import java.util.ArrayList;

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

   public User getUserByName(String twitterHandle) {
      User user = null;
      try {
         user = twitter.showUser(twitterHandle);
      } catch (TwitterException e) {
         e.getStackTrace();
      }
      return user;
   }

   public ArrayList<User> searchUser(String query) {
      ResponseList<User> userResponses = null;
      String resource = "/users/search";
      ArrayList<User> allUsers = new ArrayList<User>();

      try {
         if (decrementAndCheckRemaining(resource)) {
            int page = 0;
            // the user search returns 20 per page, we need to paginate
            do {
               userResponses = twitter.searchUsers(query, page);
               
               allUsers.addAll(userResponses);

               page++;
            } while (userResponses != null && userResponses.size() >= 20);
         } else {
            System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
            Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
         }
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      } catch (InterruptedException e) {
         System.out.println(e.getMessage());
      }
      return allUsers;
   }
}
