package ac.uk.abdn.foobs.twitter_user;

import java.util.ArrayList;
import java.util.Scanner;

import twitter4j.ResponseList;
import twitter4j.User;

public class TwitterHandleFinder {
   private RESTAPI restAPI;

   public TwitterHandleFinder(RESTAPI rRestAPI) {
      this.restAPI = rRestAPI;
   }

   public void findHandlesForEstablishements(String filename, ArrayList<Establishment> establishmentList) {
      Scanner reader = new Scanner(System.in);

      for (Establishment establishment : establishmentList) {
         ResponseList<User> users = restAPI.searchUser(establishment.getBusinessName());

         if (users != null && !users.isEmpty()) {
            System.out.println("Searched for: ");
            System.out.println(establishment.toString());
            System.out.println("Found: ");

            for (int i = 0; i < users.size(); i++) {
               System.out.println(i + ": " + users.get(i).getName() + " - " + users.get(i).getLocation() + " - " + users.get(i).getDescription());
            }

            System.out.println("Enter the number of the associated twitter account or press enter: ");
            String inputString = reader.nextLine();

            if (inputString.isEmpty()) {
               System.out.println("No associated place");
            } else {
               int input = Integer.parseInt(inputString);
               RatingsHandler.addTwitterHandleToEstablishment(filename, establishment, users.get(input).getScreenName());
            }

            System.out.println("\n**********************************************************************\n");
         }

      }
      reader.close();
   }
}
