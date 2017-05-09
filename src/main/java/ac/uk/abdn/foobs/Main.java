package ac.uk.abdn.foobs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import ac.uk.abdn.foobs.fsa.RatingsHandler;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;

import twitter4j.Status;

import ac.uk.abdn.foobs.twitter.app.AppRESTAPI;
import ac.uk.abdn.foobs.twitter.user.TwitterHandleFinder;

public class Main {
   public static void main(String[] args) {
      
      if (args.length < 1) {
         System.out.println("Run program with arguments <config.xml>");
         return;
      }

      File file = new File(args[0]);
      Config config = new Config(file);

      if (args.length < 2) {
         System.out.println("To user ratings file please supply it as an argument after the config file");
         return;
      }

      File xml = new File(args[1]);
      //findTwitterHandles(config, xml);
      testAppRESTAPI(config);
   }

   private static void findTwitterHandles(Config config, File ratingsXML) {
      ArrayList<Establishment> establishmentList = RatingsHandler.parseXml(ratingsXML);

      UserRESTAPI restAPI = new UserRESTAPI(config);

      System.out.println("Finding twitter handles for the ratings");
      TwitterHandleFinder finder = new TwitterHandleFinder(restAPI);
      finder.findHandlesForEstablishements(ratingsXML, establishmentList);
   }

   private static void testAppRESTAPI(Config config) {
      AppRESTAPI restAPI = new AppRESTAPI(config);
      queryAndWrite("food", restAPI);
      queryAndWrite("hygiene", restAPI);
      queryAndWrite("FSS", restAPI);
      queryAndWrite("FHIS", restAPI);
      queryAndWrite("sick", restAPI);
      queryAndWrite("terrible food", restAPI);
      queryAndWrite("good food", restAPI);
   }

   private static void queryAndWrite(String query, AppRESTAPI restAPI) {
      writeQueryToFile(query, restAPI.searchExactString(query, 200));
   }

   private static void writeQueryToFile(String query, List<Status> statuses) {
      Path file = Paths.get(query+".txt");
      List<String> text = new ArrayList<String>();
      for (Status status : statuses) {
         text.add(status.getText());
      }
      try {
         Files.write(file, text, Charset.forName("UTF-8"));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
