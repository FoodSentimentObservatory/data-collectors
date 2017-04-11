package ac.uk.abdn.foobs.twitter_user;

import org.w3c.dom.Element;

public class EstablishmentFactory {
   public static Establishment createEstablishment(Element element) { 
      Integer id = RatingsHandler.getIntValue(element, "FHRSID");
      String name = RatingsHandler.getStringValue(element, "BusinessName");
      String type = RatingsHandler.getStringValue(element, "BusinessType");
      String address1 = RatingsHandler.getStringValue(element, "AddressLine1");
      String address2 = RatingsHandler.getStringValue(element, "AddressLine2");
      String city = RatingsHandler.getStringValue(element, "AddressLine3");
      String postCode = RatingsHandler.getStringValue(element, "PostCode");
      String twitter = RatingsHandler.getStringValue(element, "TwitterHandle");
      Double lat = null;
      Double longitude = null;

      try {
         lat = RatingsHandler.getDoubleValue(element, "Latitude");
         longitude = RatingsHandler.getDoubleValue(element, "Longitude");


      } catch (NullPointerException e) {
         System.out.println("Failed to add GeoLocation to: " + RatingsHandler.getStringValue(element, "BusinessName"));
      }

      String address = address1 + ", " + address2;

      return new Establishment(id,name,type,address,city,postCode,lat,longitude,twitter);
   }
}
