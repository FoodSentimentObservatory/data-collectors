package ac.uk.abdn.foobs;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import ac.uk.abdn.foobs.fsa.Scores;
import ac.uk.abdn.foobs.utils.XMLUtils;

import twitter4j.GeoLocation;

public class EstablishmentFactory {
   public static Establishment createEstablishment(Element element) { 
      String[] addressStrings = new String[3];
      Integer id = XMLUtils.getIntValue(element, "FHRSID");
      String name = XMLUtils.getStringValue(element, "BusinessName");
      String type = XMLUtils.getStringValue(element, "BusinessType");
      addressStrings[0] = XMLUtils.getStringValue(element, "AddressLine1");
      addressStrings[1] = XMLUtils.getStringValue(element, "AddressLine2");
      addressStrings[2] = XMLUtils.getStringValue(element, "AddressLine3");
      String address4 = XMLUtils.getStringValue(element, "AddressLine4");
      String postCode = XMLUtils.getStringValue(element, "PostCode");
      String twitter = XMLUtils.getStringValue(element, "TwitterHandle");
      String schemeType = XMLUtils.getStringValue(element, "SchemeType");
      String ratingKey = XMLUtils.getStringValue(element, "RatingKey");
      String ratingDate = XMLUtils.getStringValue(element, "RatingDate");
      String ratingValue = XMLUtils.getStringValue(element, "RatingValue");
      String newRatingPending = XMLUtils.getStringValue(element, "NewRatingPending");
      Double lat = XMLUtils.getDoubleValue(element, "Latitude");
      Double longitude = XMLUtils.getDoubleValue(element, "Longitude");

      GeoLocation geo = null;
      if (lat != null && longitude != null) {
         geo = new GeoLocation(lat, longitude);
      }

      Scores scores = null;
      if (schemeType.equals("FHRS")) {
         Integer hygiene = XMLUtils.getIntValue(element, "Hygiene");
         Integer structural = XMLUtils.getIntValue(element, "Structural");
         Integer confidence = XMLUtils.getIntValue(element, "ConfidenceInManagement");
         if (hygiene != null && structural != null && confidence != null) {
            scores = new Scores(hygiene, structural, confidence);
         }
      }

      // Handle differences in address
      String address = "";
      String city = "";
      if (address4 == null) {
         city = addressStrings[2];
      } else {
         city = address4;
      }

      for (int i = 0; i < 3; i++) {
         if (addressStrings[i] != null) {
            if (address4 == null && i == 2) {
               break;
            }
            address = address + " " + addressStrings[i];
         }
      }

      return new Establishment(id,name,type,address,city,postCode,geo,schemeType,
            twitter,scores,ratingKey,ratingDate,ratingValue,newRatingPending);
   }
}
