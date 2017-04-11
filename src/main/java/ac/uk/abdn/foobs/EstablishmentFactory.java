package ac.uk.abdn.foobs;

import org.w3c.dom.Element;

import ac.uk.abdn.foobs.utils.XMLUtils;

public class EstablishmentFactory {
   public static Establishment createEstablishment(Element element) { 
      Integer id = XMLUtils.getIntValue(element, "FHRSID");
      String name = XMLUtils.getStringValue(element, "BusinessName");
      String type = XMLUtils.getStringValue(element, "BusinessType");
      String address1 = XMLUtils.getStringValue(element, "AddressLine1");
      String address2 = XMLUtils.getStringValue(element, "AddressLine2");
      String city = XMLUtils.getStringValue(element, "AddressLine3");
      String postCode = XMLUtils.getStringValue(element, "PostCode");
      String twitter = XMLUtils.getStringValue(element, "TwitterHandle");
      Double lat = null;
      Double longitude = null;

      try {
         lat = XMLUtils.getDoubleValue(element, "Latitude");
         longitude = XMLUtils.getDoubleValue(element, "Longitude");


      } catch (NullPointerException e) {
         System.out.println("Failed to add GeoLocation to: " + XMLUtils.getStringValue(element, "BusinessName"));
      }

      String address = address1 + ", " + address2;

      return new Establishment(id,name,type,address,city,postCode,lat,longitude,twitter);
   }
}
