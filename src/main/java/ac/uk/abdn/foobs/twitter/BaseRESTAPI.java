package ac.uk.abdn.foobs.twitter;

import java.util.HashMap;
import java.util.Map;

import ac.uk.abdn.foobs.Config;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public abstract class BaseRESTAPI {
   protected Twitter twitter;
   private Map<String, RateLimitStatusImpl> remaining = new HashMap<String, RateLimitStatusImpl>();

   public BaseRESTAPI(Config config) {
      connectToTwitter(config);
      createOrUpdateRemainingMap();
   }

   protected abstract void connectToTwitter(Config config);

   protected Boolean decrementAndCheckRemaining(String resource) {
      decrementRemainingLimit(resource);
      return checkRemainingLimit(resource);
   }

   protected int getSecondsUntilResetForResource(String resource) {
      return remaining.get(resource).getSecondsUntilReset();
   }

   private void decrementRemainingLimit(String resource) {
      remaining.get(resource).setRemaining(remaining.get(resource).getRemaining() - 1);
   }

   protected Boolean checkRemainingLimit(String resource) {
      if (remaining.get(resource).getRemaining() <= 0) {
         createOrUpdateRemainingMap();
         if (remaining.get(resource).getRemaining() <= 0) {
            return false;
         }
      }
      return true;
   }

   protected void createOrUpdateRemainingMap() {
      Map<String, RateLimitStatus> statuses = getRateLimitStatusForAll();

      for (Map.Entry<String, RateLimitStatus> entry : statuses.entrySet()) {
         remaining.put(entry.getKey(), new RateLimitStatusImpl(entry.getValue()));
      }
   }

   public RateLimitStatus getRateLimitStatusForResource(String resource) {
      try {
         return twitter.getRateLimitStatus().get(resource);
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      return null;
   }

   private Map<String, RateLimitStatus> getRateLimitStatusForAll() {
      try {
         return twitter.getRateLimitStatus();
      } catch (TwitterException e) {
         System.out.println(e.getErrorMessage());
      }
      return null;
   }

}
