package ac.uk.abdn.foobs.twitter.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RatingsHandler {

   public static ArrayList<Establishment> parseXml(String filename) {
      ArrayList<Establishment> establishmentList = new ArrayList<Establishment>();

      Document document = readXml(filename);

      if (document == null) {
         return establishmentList;
      }

      Element rootElement = document.getDocumentElement();
      NodeList   establishments = rootElement.getElementsByTagName("EstablishmentDetail");

      for (int i = 0; i < establishments.getLength(); i++) {
         Establishment establishment = EstablishmentFactory.createEstablishment((Element)establishments.item(i));
         if (establishment != null) {
            establishmentList.add(establishment);
         }
      }

      return establishmentList;
   }

   public static void addTwitterHandleToEstablishment(String filename, Establishment establishment, String twitter) {
      Document document = readXml(filename);

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

      writeToFile(filename, document);
   }

   private static void writeToFile(String filename, Document doc) {
      try {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(new File(filename));
         transformer.transform(source, result);
      } catch (TransformerException e) {
         System.out.println("Failed to write to file");
      }
   }

   private static Element findElementByEstablishmentID(NodeList list, Integer estID) {
      Element found = null;

      if (list == null) {
         System.out.println("No element list supplied");
         return found;
      }

      for (int i = 0; i < list.getLength(); i++) {
         Element current = (Element)list.item(i);
         Integer id = getIntValue(current, "FHRSID");
         if (id.equals(estID)) {
            return current;
         }
      }

      return found;
   }

   private static Document readXml(String filename) {
      Document doc = null;
      try {
         File file = new File(filename);
         DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         doc = documentBuilder.parse(file);

      } catch (ParserConfigurationException e) { 
      } catch (SAXException e){
      } catch (IOException e) {
      }
      return doc;
   }

   public static String getStringValue(Element element, String tagName) {
      String stringValue = null;

      try {
         NodeList nl = element.getElementsByTagName(tagName);
         if (nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            stringValue = el.getFirstChild().getNodeValue();
         }
      } catch (NullPointerException e) {
         //if value is not existent return null
      }

      return stringValue;
   }

   public static Integer getIntValue(Element element, String tagName) {
      Integer value = null;
      if (getStringValue(element,tagName) == null || getStringValue(element, tagName) == "null")
      {
         return value;
      }
      return Integer.parseInt(getStringValue(element, tagName));
   }

   public static Double getDoubleValue(Element element, String tagName) {
      Double value = null;
      if (getStringValue(element,tagName) == null || getStringValue(element, tagName) == "null")
      {
         return value;
      }
      return Double.parseDouble(getStringValue(element, tagName));
   }
}
