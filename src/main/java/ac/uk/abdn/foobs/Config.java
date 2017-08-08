package ac.uk.abdn.foobs;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ac.uk.abdn.foobs.utils.XMLUtils;

public class Config {
   private String twitterAppConsumerKey;
   private String twitterAppConsumerSecret;
   private String twitterUserConsumerKey;
   private String twitterUserConsumerSecret;
   private String twitterUserAccessToken;
   private String twitterUserAccessTokenSecret;
   private String dbConnectionUrl;
   private String ratingFile;
   private String ratingFileCountry;
   private String keywordsFilename;
   private String keywordsFileEncoding;
   private Integer parseAndUploadRatings;
   private Integer findTwitterAccounts;
   private Integer findTweetsFromRestaurants;
   private Integer findTweetsContainingKeywords;
   
   public Config(File file) {
      readAndSetConfig(file);
   }
//chnage added by Nikol
   private void readAndSetConfig(File file) {
      Document document = XMLUtils.readXml(file);

      if (document == null) {
         System.out.println("Failed to read config file");
         return;
      }

      Element rootElement = document.getDocumentElement();

      Element twitterApp = (Element)rootElement.getElementsByTagName("TwitterApp").item(0);

      this.twitterAppConsumerKey = XMLUtils.getStringValue(twitterApp, "ConsumerKey");
      this.twitterAppConsumerSecret = XMLUtils.getStringValue(twitterApp, "ConsumerSecret");

      Element twitterUser = (Element)rootElement.getElementsByTagName("TwitterUser").item(0);

      this.twitterUserConsumerKey = XMLUtils.getStringValue(twitterUser, "ConsumerKey");
      this.twitterUserConsumerSecret = XMLUtils.getStringValue(twitterUser, "ConsumerSecret");
      this.twitterUserAccessToken = XMLUtils.getStringValue(twitterUser, "UserAccessToken");
      this.twitterUserAccessTokenSecret = XMLUtils.getStringValue(twitterUser, "UserAccessTokenSecret");

      Element db = (Element)rootElement.getElementsByTagName("Db").item(0);
      this.dbConnectionUrl = XMLUtils.getStringValue(db, "ConnectionUrl");

      Element rating = (Element)rootElement.getElementsByTagName("Rating").item(0);
      this.ratingFile = XMLUtils.getStringValue(rating, "RatingFile");
      this.ratingFileCountry = XMLUtils.getStringValue(rating, "Country");

      Element tasks = (Element)rootElement.getElementsByTagName("Tasks").item(0);
      this.parseAndUploadRatings = XMLUtils.getIntValue(tasks, "ParseAndUploadRatings");
      this.findTwitterAccounts = XMLUtils.getIntValue(tasks, "FindTwitterAccounts");
      this.findTweetsFromRestaurants = XMLUtils.getIntValue(tasks, "FindTweetsFromRestaurants");
      this.findTweetsContainingKeywords = XMLUtils.getIntValue(tasks, "FindTweetsContainingKeywords");
      
      Element keywordsFile = (Element)rootElement.getElementsByTagName("KeywordsFile").item(0);
      this.keywordsFilename = XMLUtils.getStringValue(keywordsFile, "Filename");
      this.keywordsFileEncoding = XMLUtils.getStringValue(keywordsFile, "FileEncoding");
      
   }

   /**
    * @return the twitterAppConsumerKey
    */
   public String getTwitterAppConsumerKey() {
      return twitterAppConsumerKey;
   }

   /**
    * @return the twitterAppConsumerSecret
    */
   public String getTwitterAppConsumerSecret() {
      return twitterAppConsumerSecret;
   }

   /**
    * @return the twitterUserConsumerKey
    */
   public String getTwitterUserConsumerKey() {
      return twitterUserConsumerKey;
   }

   /**
    * @return the twitterUserConsumerSecret
    */
   public String getTwitterUserConsumerSecret() {
      return twitterUserConsumerSecret;
   }

   /**
    * @return the twitterUserAccessToken
    */
   public String getTwitterUserAccessToken() {
      return twitterUserAccessToken;
   }

   /**
    * @return the twitterUserAccessTokenSecret
    */
   public String getTwitterUserAccessTokenSecret() {
      return twitterUserAccessTokenSecret;
   }

   /**
    * @return the dbConnectionUrl
    */
   public String getDbConnectionUrl() {
      return dbConnectionUrl;
   }

   /**
    * @return the ratingFile
    */
   public String getRatingFile() {
      return ratingFile;
   }

   /**
    * @param ratingFile the ratingFile to set
    */
   public void setRatingFile(String ratingFile) {
      this.ratingFile = ratingFile;
   }

   /**
    * @return the ratingFileCountry
    */
   public String getRatingFileCountry() {
      return ratingFileCountry;
   }

   /**
    * @param ratingFileCountry the ratingFileCountry to set
    */
   public void setRatingFileCountry(String ratingFileCountry) {
      this.ratingFileCountry = ratingFileCountry;
   }

   /**
    * @return the parseAndUploadRatings
    */
   public Integer getParseAndUploadRatings() {
      return parseAndUploadRatings;
   }

   /**
    * @param parseAndUploadRatings the parseAndUploadRatings to set
    */
   public void setParseAndUploadRatings(Integer parseAndUploadRatings) {
      this.parseAndUploadRatings = parseAndUploadRatings;
   }

   /**
    * @return the findTwitterAccounts
    */
   public Integer getFindTwitterAccounts() {
      return findTwitterAccounts;
   }

   /**
    * @param findTwitterAccounts the findTwitterAccounts to set
    */
   public void setFindTwitterAccounts(Integer findTwitterAccounts) {
      this.findTwitterAccounts = findTwitterAccounts;
   }

   /**
    * @return the findTweetsFromRestaurants
    */
   public Integer getFindTweetsFromRestaurants() {
      return findTweetsFromRestaurants;
   }

   /**
    * @param findTweetsFromRestaurants the findTweetsFromRestaurants to set
    */
   public void setFindTweetsFromRestaurants(Integer findTweetsFromRestaurants) {
      this.findTweetsFromRestaurants = findTweetsFromRestaurants;
   }

public String getKeywordsFilename() {
	return keywordsFilename;
}

public String getKeywordsFileEncoding() {
	return keywordsFileEncoding;
}

public Integer getFindTweetsContainingKeywords() {
	return findTweetsContainingKeywords;
}

}
