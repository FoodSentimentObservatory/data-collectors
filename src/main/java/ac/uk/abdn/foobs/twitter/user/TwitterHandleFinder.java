package ac.uk.abdn.foobs.twitter.user;

import java.io.File;
import java.util.ArrayList;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.fsa.RatingsHandler;

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
            if (handle != null) {
               RatingsHandler.addTwitterHandleToEstablishment(file, establishment, handle);
            }
         }
      }
   }

   private String findHandleForEstablishment(Establishment establishment) {
      String twitterHandle = null;

      ArrayList<User> responeseUsers = restAPI.searchUser(establishment.getBusinessName());
      ArrayList<User> filteredUsers = new ArrayList<User>(filterUsersBasedOnLocation(responeseUsers, establishment));

      if (!filteredUsers.isEmpty()) {
         System.out.println("Searched for:\n ");
         System.out.println(establishment.toString());
         System.out.println("Found:\n ");

         for (int i = 0; i < filteredUsers.size(); i++) {
            System.out.println(i + "\t: " + filteredUsers.get(i).getName() + "\t -\t " + filteredUsers.get(i).getLocation() + "\n\n" + filteredUsers.get(i).getDescription());
            System.out.println("\n---------------------------------------------------\n");
         }

         boolean validInput = false;

         do {
            System.out.println("Enter the number of the associated twitter account, enter 'skip' to skip establishment or enter if 'none' if none of the accounts match the establishment: ");
            String input = System.console().readLine();

            if (input.isEmpty()) {
               System.out.println("Invalid input, try again.");
               validInput = false;
            } else if (input.equals("skip")) {
               twitterHandle = null;
               validInput = true;
               break;
            } else if (input.equals("none")) {
               twitterHandle = "NONE";
               validInput = true;
               break;
            }

            try {
               int userId = Integer.parseInt(input);
               twitterHandle = filteredUsers.get(userId).getScreenName();
               validInput = true;
            } catch (NumberFormatException e) {
               System.out.println("Invalid input, try again.");
               validInput = false;
            }
         } while (!validInput);


         System.out.println("\n**********************************************************************\n");

      } else {
         // add NONE in place of the twitter handle
         twitterHandle = "NONE";
      }
      
      return twitterHandle;
   }

   private ArrayList<User> filterUsersBasedOnLocation(ArrayList<User> users, Establishment establishment) {
      ArrayList<User> filteredUsers = new ArrayList<User>();
      String userLocation = "";
      User user = null;
      for (int i = 0; i < users.size(); i++) {
         user = users.get(i);
         userLocation = user.getLocation();
         String[] establishmentAddressWords = establishment.getAddress().split(" ");

         if (userLocation.contains("UK") || userLocation.contains("United Kingdom")) {
            filteredUsers.add(user);
            continue;
         } else if (establishment.getCity() != null && userLocation.contains(establishment.getCity())) {
            filteredUsers.add(user);
            continue;
         } else if (establishment.getPostCode() != null && userLocation.contains(establishment.getPostCode())) {
            filteredUsers.add(user);
            continue;
         }

         for (int j = 0; j < establishmentAddressWords.length; j++) {
            if (userLocation.contains(establishmentAddressWords[j])) {
               filteredUsers.add(user);
               break;
            }
         }

      }

      return filteredUsers;
   }
}
