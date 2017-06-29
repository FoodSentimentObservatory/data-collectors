package ac.uk.abdn.foobs.twitter.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ac.uk.abdn.foobs.Config;
import ac.uk.abdn.foobs.twitter.BaseRESTAPI;
import twitter4j.GeoLocation;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
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
      int prevTweetSize = tweets.size();
      long lastId = Long.MAX_VALUE;
      query.setLang("en");
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
               prevTweetSize = tweets.size();
               tweets.addAll(result.getTweets());
               // This will ensure that if duplicates are inserted then no more queries
               if (prevTweetSize + result.getTweets().size() > tweets.size() 
                     || result.getTweets().size() == 0) {
                  break;
               }
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
      int prevTweetSize = tweets.size();
      Paging paging = new Paging();
      int page = 1;
      boolean protectedUser = false;
      try {
         protectedUser = getUserByName(userHandle).isProtected();
      } catch (NullPointerException e) {
         System.out.println("No user fount by: " + userHandle);
         return tweets;
      }

      if (!protectedUser) {
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
                  prevTweetSize = tweets.size();
                  tweets.addAll(statuses);
                  // This will ensure that if duplicates are inserted then no more queries
                  if (prevTweetSize + statuses.size() > tweets.size() 
                        || statuses.size() == 0) {
                     break;
                  }
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
      } else {
         System.out.println(userHandle + " is protected");
      }

      return tweets;
   }

   public Set<Status> searchList(List<String> words, int numberOfTweets) {
      Query query = new Query();
      String queryString = words.stream().map(Object::toString).collect(Collectors.joining("\" OR \""));
      queryString = "\""+queryString+"\"";
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

	public Set<Status> searchExactStringGeoCoded(String string, int numberOfTweets, GeoLocation centroid, double radius,
			Unit unit) {

		Query query = new Query();
		query.setQuery("\"" + string + "\"");
		query.setGeoCode(centroid, radius, unit);

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

   public User getUserByName(String twitterHandle) {
      User user = null;
      try {
         user = twitter.showUser(twitterHandle);
      } catch (TwitterException e) {
         e.getStackTrace();
      }
      return user;
   }
}
