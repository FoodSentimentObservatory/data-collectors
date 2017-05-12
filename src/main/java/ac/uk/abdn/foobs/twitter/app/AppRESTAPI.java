package ac.uk.abdn.foobs.twitter.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ac.uk.abdn.foobs.Config;
import ac.uk.abdn.foobs.twitter.BaseRESTAPI;

import twitter4j.Paging;
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

   public Set<Status> search(Query query, int numberOfTweets) {
      String resource = "/search/tweets";
      QueryResult result = null;
      Set<Status> tweets = new HashSet<Status>();
      long lastId = Long.MAX_VALUE;

      while (tweets.size() < numberOfTweets) {
         if (numberOfTweets - tweets.size() > 100) {
            // this is the maximum that could be retrieved at once
            query.setCount(100);
         } else {
            query.setCount(numberOfTweets - tweets.size());
         }

         try {
            if (decrementAndCheckRemaining(resource)) {
               result = twitter.search(query);
               tweets.addAll(result.getTweets());
               for (Status tweet : tweets) {
                  if (tweet.getId() < lastId) {
                     lastId = tweet.getId();
                  }
               }
               query.setMaxId(lastId - 1);
            } else {
               System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
               Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
            }
         } catch (TwitterException e) {
            System.out.println(e.getErrorMessage());
         } catch (InterruptedException e) {
            System.out.println(e.getMessage());
         }
      }
      
      return tweets;
   }

   public Set<Status> showTweetsByUser(String userHandle, int numberOfTweets) {
      String resource = "/statuses/user_timeline";
      ResponseList<Status> statuses = null;
      Set<Status> tweets = new HashSet<Status>();
      Paging paging = new Paging();
      int page = 1;

      while (tweets.size() < numberOfTweets) {
         paging.setPage(page);
         if (numberOfTweets - tweets.size() > 200) {
            paging.setCount(200);
         } else {
            paging.setCount(numberOfTweets - tweets.size());
         }
         try {
            if (decrementAndCheckRemaining(resource)) {
               statuses = twitter.getUserTimeline(userHandle, paging);
               tweets.addAll(statuses);
            } else {
               System.out.println("Twitter Limit exceeded for " + resource + ", wait for " + getSecondsUntilResetForResource(resource) + " seconds");
               Thread.sleep(getSecondsUntilResetForResource(resource)*1000);
            }
         } catch (TwitterException e) {
            System.out.println(e.getErrorMessage());
         } catch (InterruptedException e) {
            System.out.println(e.getMessage());
         }

         page++;
      }

      return tweets;
   }

   public Set<Status> searchList(List<String> words, int numberOfTweets) {
      Query query = new Query();
      String queryString = words.stream().map(Object::toString).collect(Collectors.joining(" OR "));
      System.out.println(queryString);
      query.setQuery(queryString);
      return search(query, numberOfTweets);
   }

   public Set<Status> searchRepliesToUser(String userHandle, int numberOfTweets) {
      Query query = new Query();
      query.setQuery("to:"+userHandle);
      return search(query, numberOfTweets);
   }

   public Set<Status> searchMentionsOfUser(String userHandle, int numberOfTweets) {
      Query query = new Query();
      query.setQuery("@"+userHandle);
      return search(query, numberOfTweets);
   }

   public Set<Status> searchExactString(String string, int numberOfTweets) {
      Query query = new Query();
      query.setQuery("\""+string+"\"");
      return search(query, numberOfTweets);
   }

   public Set<Status> searchHashTag(String hashtag, int numberOfTweets) {
      Query query = new Query();
      query.setQuery("#"+hashtag);
      return search(query, numberOfTweets);
   }
}
