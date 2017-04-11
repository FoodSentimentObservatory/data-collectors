package ac.uk.abdn.foobs.twitter.user;

import java.io.File;
import java.util.ArrayList;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.fsa.RatingsHandler;

public class TwitterMain {
   public static void main(String[] args) {
      if (args.length < 1) {
         System.out.println("Run program with argument <ratings.xml>");
         return;
      }

      File ratingsXML = new File(args[0]);

      ArrayList<Establishment> establishmentList = RatingsHandler.parseXml(ratingsXML);

      RESTAPI restAPI = new RESTAPI();

      System.out.println("Finding twitter handles for the ratings");
      TwitterHandleFinder finder = new TwitterHandleFinder(restAPI);
      finder.findHandlesForEstablishements(ratingsXML, establishmentList);

   }
}
