package ac.uk.abdn.foobs.twitter.user;

import java.util.ArrayList;

public class TwitterMain {
   public static void main(String[] args) {
      if (args.length < 1) {
         System.out.println("Run program with argument <ratings.xml>");
         return;
      }

      ArrayList<Establishment> establishmentList = RatingsHandler.parseXml(args[0]);

      RESTAPI restAPI = new RESTAPI();

      System.out.println("Finding twitter handles for the ratings");
      TwitterHandleFinder finder = new TwitterHandleFinder(restAPI);
      finder.findHandlesForEstablishements(args[0], establishmentList);

   }
}
