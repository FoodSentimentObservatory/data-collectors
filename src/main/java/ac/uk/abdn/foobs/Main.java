package ac.uk.abdn.foobs;

import java.io.File;
import java.util.ArrayList;

import ac.uk.abdn.foobs.fsa.RatingsHandler;
import ac.uk.abdn.foobs.twitter.user.UserRESTAPI;
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
      findTwitterHandles(config, xml);
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
   }
}
