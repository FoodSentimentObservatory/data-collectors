package ac.uk.abdn.foobs.twitter;

// NOT USED MARK FOR REMOVAL

import twitter4j.RateLimitStatus;

@SuppressWarnings("serial")
public class RateLimitStatusImpl implements RateLimitStatus {
   private int limit;
   private int remaining;
   private int resetTimeInSeconds;
   private int secondsUntilReset;

   public RateLimitStatusImpl(RateLimitStatus rateLimitStatus) {
      this.limit = rateLimitStatus.getLimit();
      this.remaining = rateLimitStatus.getRemaining();
      this.resetTimeInSeconds = rateLimitStatus.getResetTimeInSeconds();
      this.secondsUntilReset = rateLimitStatus.getSecondsUntilReset();
   }

   public void update(RateLimitStatus rateLimitStatus) {
      this.limit = rateLimitStatus.getLimit();
      this.remaining = rateLimitStatus.getRemaining();
      this.resetTimeInSeconds = rateLimitStatus.getResetTimeInSeconds();
      this.secondsUntilReset = rateLimitStatus.getSecondsUntilReset();
   }


   /**
   * @return the limit
   */
   public int getLimit() {
      return limit;
   }

   /**
   * @return the remaining
   */
   public int getRemaining() {
      return remaining;
   }

   /**
   * @param remaining the remaining to set
   */
   public void setRemaining(int remaining) {
      this.remaining = remaining;
   }

   /**
   * @return the resetTimeInSeconds
   */
   public int getResetTimeInSeconds() {
      return resetTimeInSeconds;
   }

   /**
    * @return the secondsUntilReset
    */
   public int getSecondsUntilReset() {
      return secondsUntilReset;
   }
}
