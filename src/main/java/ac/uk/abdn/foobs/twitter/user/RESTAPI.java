package ac.uk.abdn.foobs.twitter.user;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class RESTAPI {
   private Twitter twitter;

   public RESTAPI() {
      twitter = TwitterFactory.getSingleton();
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
