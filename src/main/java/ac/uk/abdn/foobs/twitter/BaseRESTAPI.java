package ac.uk.abdn.foobs.twitter;

import java.util.Map;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public abstract class BaseRESTAPI {
   protected Twitter twitter;

   public Map<String, RateLimitStatus> getRateLimitStatusForResource(String resources) {
      try {
         return twitter.getRateLimitStatus(resources);
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      return null;
   }

   public Map<String, RateLimitStatus> getRateLimitStatusForAll() {
      try {
         return twitter.getRateLimitStatus();
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      return null;
   }

}
