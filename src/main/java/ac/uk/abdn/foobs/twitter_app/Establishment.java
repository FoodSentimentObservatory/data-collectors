package ac.uk.abdn.foobs.twitter_app;

import twitter4j.GeoLocation;

public class Establishment {
   private int FHRSID;
   private String BusinessName;
   private String BusinessType;
   private String Address;
   private String City;
   private String PostCode;
   private String twitterHandle;
   private GeoLocation location;

   public Establishment(int rId, String rName, String rType, String rAddress, String rCity, String rPostCode, Double lat, Double longitude) {
      this.FHRSID = rId;
      this.BusinessName = rName;
      this.BusinessType = rType;
      this.Address = rAddress;
      this.City = rCity;
      this.PostCode = rPostCode;
      if (lat == null || longitude == null) {
         this.location = null;
      } else {
         this.location = new GeoLocation(lat, longitude);
      }
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
}
