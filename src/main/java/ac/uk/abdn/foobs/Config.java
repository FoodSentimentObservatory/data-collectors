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

   public Config(File file) {
      readAndSetConfig(file);
   }

   private void readAndSetConfig(File file) {
      Document document = XMLUtils.readXml(file);

      if (document == null) {
         System.out.println("Failed to read config file");
         return;
      }

      Element rootElement = document.getDocumentElement();

      Element twitterApp = (Element)rootElement.getElementsByTagName("TwitterApp").item(0);

      twitterAppConsumerKey = XMLUtils.getStringValue(twitterApp, "ConsumerKey");
      twitterAppConsumerSecret = XMLUtils.getStringValue(twitterApp, "ConsumerSecret");

      Element twitterUser = (Element)rootElement.getElementsByTagName("TwitterUser").item(0);

      twitterUserConsumerKey = XMLUtils.getStringValue(twitterUser, "ConsumerKey");
      twitterUserConsumerSecret = XMLUtils.getStringValue(twitterUser, "ConsumerSecret");
      twitterUserAccessToken = XMLUtils.getStringValue(twitterUser, "UserAccessToken");
      twitterUserAccessTokenSecret = XMLUtils.getStringValue(twitterUser, "UserAccessTokenSecret");

      Element db = (Element)rootElement.getElementsByTagName("Db").item(0);
      dbConnectionUrl = XMLUtils.getStringValue(db, "ConnectionUrl");
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
}
