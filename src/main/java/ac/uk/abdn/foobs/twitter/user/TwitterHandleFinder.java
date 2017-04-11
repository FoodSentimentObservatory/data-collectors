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
      Scanner reader = new Scanner(System.in);

      for (Establishment establishment : establishmentList) {
         ResponseList<User> users = restAPI.searchUser(establishment.getBusinessName());

         if (users != null && !users.isEmpty()) {
            System.out.println("Searched for: ");
            System.out.println(establishment.toString());
            System.out.println("Found: ");

            for (int i = 0; i < users.size(); i++) {
               System.out.println(i + "\t: " + users.get(i).getName() + "\t -\t " + users.get(i).getLocation() + "\n\n" + users.get(i).getDescription());
               System.out.println("\n---------------------------------------------------\n");
            }

            System.out.println("Enter the number of the associated twitter account or press enter: ");
            String inputString = reader.nextLine();

            if (inputString.isEmpty()) {
               System.out.println("No associated place");
            } else {
               int input = Integer.parseInt(inputString);
               RatingsHandler.addTwitterHandleToEstablishment(file, establishment, users.get(input).getScreenName());
            }

            System.out.println("\n**********************************************************************\n");
         } else {
            // add NONE in place of the twitter handle
            RatingsHandler.addTwitterHandleToEstablishment(file, establishment, "NONE");
         }

      }
      reader.close();
   }
}
