package ac.uk.abdn.foobs.twitter_app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
      NodeList establishments = rootElement.getElementsByTagName("EstablishmentDetail");
      if (establishments != null) {
         for (int i = 0; i < establishments.getLength(); i++) {
            Establishment establishment = EstablishmentFactory.createEstablishment((Element)establishments.item(i));
            if (establishment != null) {
               establishmentList.add(establishment);
            }
         }
      }

      return establishmentList;
   }

   private static Document readXml(String filename){
      Document document = null;
      try {
         File file = new File(filename);
         DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         document = documentBuilder.parse(file);
      } catch (ParserConfigurationException e) { 
      } catch (SAXException e){
      } catch (IOException e) {
      }
      return document;
   }

   public static String getStringValue(Element element, String tagName) {
      String stringValue = null;

      NodeList nl = element.getElementsByTagName(tagName);
      if (nl != null && nl.getLength() > 0) {
         Element el = (Element)nl.item(0);
         stringValue = el.getFirstChild().getNodeValue();
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
