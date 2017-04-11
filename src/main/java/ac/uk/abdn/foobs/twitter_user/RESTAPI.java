package ac.uk.abdn.foobs.twitter_user;

import twitter4j.Query;
import twitter4j.QueryResult;
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
