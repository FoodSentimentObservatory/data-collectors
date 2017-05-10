package ac.uk.abdn.foobs.fsa;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.EstablishmentUtil;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;
import ac.uk.abdn.foobs.utils.XMLUtils;

public class RatingsHandler {

   public static ArrayList<Establishment> parseXml(File file, UserRESTAPI restAPI) {
      ArrayList<Establishment> establishmentList = new ArrayList<Establishment>();

      Document document = XMLUtils.readXml(file);

      if (document == null) {
         return establishmentList;
      }

      Element rootElement = document.getDocumentElement();
      NodeList establishments = rootElement.getElementsByTagName("EstablishmentDetail");

      for (int i = 0; i < establishments.getLength(); i++) {
         Establishment establishment = EstablishmentUtil.createEstablishment((Element)establishments.item(i), restAPI);
         if (establishment != null) {
            establishmentList.add(establishment);
         }
      }

      return establishmentList;
   }

   public static void addTwitterHandleToEstablishmentInXML(File file, Establishment establishment, String twitter) {

      Document document = XMLUtils.readXml(file);

      if (document == null) {
         System.out.println("Failed to read ratings file, handle for " + establishment.getBusinessName() + " will not be added");
         return;
      }

      Element rootElement = document.getDocumentElement();
      NodeList establishments = rootElement.getElementsByTagName("EstablishmentDetail");

      Element placeToAddTo = findElementByEstablishmentID(establishments, establishment.getFHRSID());

      if (placeToAddTo == null) {
         System.out.println("Failed to find " + establishment.getBusinessName() + ", can't add Twitter handle");
         return;
      }

      Element handle = document.createElement("TwitterHandle");
      handle.appendChild(document.createTextNode(twitter));
      placeToAddTo.appendChild(handle);

      XMLUtils.writeDocumentToXMLFile(file, document);
   }


   private static Element findElementByEstablishmentID(NodeList list, Integer estID) {
      Element found = null;

      if (list == null) {
         System.out.println("No element list supplied");
         return found;
      }

      for (int i = 0; i < list.getLength(); i++) {
         Element current = (Element)list.item(i);
         Integer id = XMLUtils.getIntValue(current, "FHRSID");
         if (id.equals(estID)) {
            return current;
         }
      }

      return found;
   }
}
