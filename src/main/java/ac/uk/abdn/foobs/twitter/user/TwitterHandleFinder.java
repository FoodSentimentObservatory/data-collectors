package ac.uk.abdn.foobs.twitter.user;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.fsa.RatingsHandler;

import twitter4j.ResponseList;
import twitter4j.User;

public class TwitterHandleFinder {
   private UserRESTAPI restAPI;

   public TwitterHandleFinder(UserRESTAPI rRestAPI) {
      this.restAPI = rRestAPI;
   }

   public void findHandlesForEstablishements(File file, ArrayList<Establishment> establishmentList) {

      for (Establishment establishment : establishmentList) {
         if (establishment.getTwitterHandle() == null) {
            String handle = findHandleForEstablishment(establishment);
            RatingsHandler.addTwitterHandleToEstablishment(file, establishment, handle);
         }
      }
   }

   private String findHandleForEstablishment(Establishment establishment) {
      String twitterHandle = null;
      Scanner reader = new Scanner(System.in);

      ResponseList<User> users = restAPI.searchUser(establishment.getBusinessName());

      if (users != null && !users.isEmpty()) {
         System.out.println("Searched for:\n ");
         System.out.println(establishment.toString());
         System.out.println("Found:\n ");

         for (int i = 0; i < users.size(); i++) {
            System.out.println(i + "\t: " + users.get(i).getName() + "\t -\t " + users.get(i).getLocation() + "\n\n" + users.get(i).getDescription());
            System.out.println("\n---------------------------------------------------\n");
         }

         System.out.println("Enter the number of the associated twitter account or press enter: ");
         String inputString = reader.nextLine();

         if (inputString.isEmpty()) {
            System.out.println("No associated place");
            twitterHandle = "NONE";
         } else {
            int input = Integer.parseInt(inputString);
            twitterHandle = users.get(input).getScreenName();
         }
         System.out.println("\n**********************************************************************\n");

      } else {
         // add NONE in place of the twitter handle
         twitterHandle = "NONE";
      }
      
      reader.close();
      return twitterHandle;
   }
}
