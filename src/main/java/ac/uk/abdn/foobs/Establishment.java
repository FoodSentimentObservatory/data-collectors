package ac.uk.abdn.foobs;

import ac.uk.abdn.foobs.fsa.Scores;

import twitter4j.GeoLocation;

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
   private String ratingDate;
   private String ratingValue;
   private String newRatingPending;

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
      this.ratingDate = rRatingDate;
      this.ratingValue = rRatingValue;
      this.newRatingPending = rNewRatingPending;
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
   public String getRatingDate() {
      return ratingDate;
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
}
