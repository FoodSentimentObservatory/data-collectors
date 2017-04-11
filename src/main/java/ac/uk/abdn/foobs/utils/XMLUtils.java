package ac.uk.abdn.foobs.utils;

import java.io.File;
import java.io.IOException;

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

public class XMLUtils {

   public static Document readXml(File file) {
      Document doc = null;

      try {
         DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
         doc = documentBuilder.parse(file);
      } catch (ParserConfigurationException e) { 
         e.printStackTrace();
      } catch (SAXException e){
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

      return doc;
   }

   public static void writeDocumentToXMLFile(File file, Document doc) {
      try {
         TransformerFactory transformerFactory = TransformerFactory.newInstance();
         Transformer transformer = transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result = new StreamResult(file);
         transformer.transform(source, result);
      } catch (TransformerException e) {
         System.out.println("Failed to write to file");
      }
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
      if (getStringValue(element,tagName) == null)
      {
         return value;
      }
      return Integer.parseInt(getStringValue(element, tagName));
   }

   public static Double getDoubleValue(Element element, String tagName) {
      Double value = null;
      if (getStringValue(element,tagName) == null)
      {
         return value;
      }
      return Double.parseDouble(getStringValue(element, tagName));
   }
}
