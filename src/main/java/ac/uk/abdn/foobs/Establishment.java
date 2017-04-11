package ac.uk.abdn.foobs;

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

   public Establishment(Integer rId, String rName, String rType, String rAddress, String rCity, String rPostCode, Double lat, Double longitude, String rTwitterHandle) {
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
      this.twitterHandle = rTwitterHandle;
   }

   public String toString() {
      String value = "BusinessName: " + BusinessName + "\n"+
                     "FHRSID: " + FHRSID + "\n" +
                     "BusinessType: " + BusinessType + "\n" +
                     "Address: " + Address + "\n" +
                     "City: " + City + "\n" + 
                     "PostCode: " + PostCode + "\n" +
                     "twitterHandle: " + twitterHandle + "\n" +
                     "location: " + location.toString() + "\n";
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
}