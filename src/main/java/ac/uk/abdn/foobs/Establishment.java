package ac.uk.abdn.foobs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ac.uk.abdn.foobs.fsa.Scores;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;

import twitter4j.GeoLocation;
import twitter4j.User;

public class Establishment {
   private Integer FHRSID;
   private String BusinessName;
   private String BusinessType;
   private String Address;
   private String City;
   private String PostCode;
   private String twitterHandle;
   private GeoLocation location;
   private String schemeType;
   private Scores scores;
   private String ratingKey;
   private Date ratingDate;
   private String ratingValue;
   private String newRatingPending;
   private User twitter = null;

   public Establishment(Integer rId, String rName, String rType, String rAddress,
         String rCity, String rPostCode, GeoLocation rLocation, String rSchemeType,
         String rTwitterHandle, Scores rScores, String rRatingKey,
         String rRatingDate, String rRatingValue, String rNewRatingPending) {
      this.FHRSID = rId;
      this.BusinessName = rName;
      this.BusinessType = rType;
      this.Address = rAddress;
      this.City = rCity;
      this.PostCode = rPostCode;
      this.location = rLocation;
      this.twitterHandle = rTwitterHandle;
      this.scores = rScores;
      this.schemeType = rSchemeType;
      this.ratingKey = rRatingKey;
      this.ratingDate = convertDateString(rRatingDate);
      this.ratingValue = rRatingValue;
      this.newRatingPending = rNewRatingPending;
   }

   private Date convertDateString(String string) {
      Date date = null;
      try {
         DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         date = format.parse(string);

      } catch (ParseException e) {
         e.printStackTrace();
      }
      return date;
   }

   public void setTwitterUser(UserRESTAPI restAPI) {
      if (twitterHandle != null && !twitterHandle.equals("NONE")) {
         this.twitter = restAPI.getUserByName(this.twitterHandle);
      }
   }

   public String toString() {
      String scoreString = "null";
      String locationString = "null";
      if (scores != null) {
         scoreString = scores.toString();
      }
      if (location != null) {
         locationString = location.toString();
      }

      String value = "BusinessName: " + BusinessName + "\n"+
                     "FHRSID: " + FHRSID + "\n" +
                     "BusinessType: " + BusinessType + "\n" +
                     "Address: " + Address + "\n" +
                     "City: " + City + "\n" + 
                     "PostCode: " + PostCode + "\n" +
                     "twitterHandle: " + twitterHandle + "\n" +
                     "location: " + locationString + "\n" +
                     "SchemeType: " + schemeType + "\n" +
                     "ratingKey: " + ratingKey + "\n" +
                     "ratingDate: " + ratingDate + "\n" +
                     "ratingValue: " + ratingValue + "\n" +
                     "newRatingPending: " + newRatingPending + "\n" +
                     "scores: " + scoreString + "\n";
      return value;
   }

   /**
    * @return the fHRSID
    */
   public int getFHRSID() {
      return FHRSID;
   }

   /**
    * @return the businessName
    */
   public String getBusinessName() {
      return BusinessName;
   }

   /**
    * @return the businessType
    */
   public String getBusinessType() {
      return BusinessType;
   }

   /**
    * @return the address
    */
   public String getAddress() {
      return Address;
   }

   /**
   * @return the city
   */
   public String getCity() {
      return City;
   }

   /**
   * @return the postCode
   */
   public String getPostCode() {
      return PostCode;
   }

   /**
    * @return the twitterHandle
    */
   public String getTwitterHandle() {
      return twitterHandle;
   }

   /**
    * @param twitterHandle the twitterHandle to set
    */
   public void setTwitterHandle(String twitterHandle) {
      this.twitterHandle = twitterHandle;
   }

   /**
    * @return the location
    */
   public GeoLocation getLocation() {
      return location;
   }

   /**
   * @return the schemeType
   */
   public String getSchemeType() {
      return schemeType;
   }

   /**
    * @param schemeType the schemeType to set
    */
   public void setSchemeType(String schemeType) {
      this.schemeType = schemeType;
   }

   /**
    * @return the scores
    */
   public Scores getScores() {
      return scores;
   }

   /**
    * @return the ratingKey
    */
   public String getRatingKey() {
      return ratingKey;
   }

   /**
   * @return the ratingDate
   */
   public Date getRatingDate() {
      return ratingDate;
   }

   /**
    * @param ratingDate the ratingDate to set
    */
   public void setRatingDate(Date ratingDate) {
      this.ratingDate = ratingDate;
   }

   /**
    * @return the ratingValue
    */
   public String getRatingValue() {
      return ratingValue;
   }

   /**
    * @return the newRatingPending
    */
   public String getNewRatingPending() {
      return newRatingPending;
   }

   /**
    * @return the twitter
    */
   public User getTwitter() {
      return twitter;
   }

   /**
    * @param twitter the twitter to set
    */
   public void setTwitter(User twitter) {
      this.twitter = twitter;
   }
}
