package ac.uk.abdn.foobs.twitter.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import ac.uk.abdn.foobs.db.DAO;
import ac.uk.abdn.foobs.db.entity.AddressEntity;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.db.entity.PremisesEntity;
import ac.uk.abdn.foobs.db.entity.UserAccountEntity;

import twitter4j.User;

public class TwitterHandleFinder {
   private UserRESTAPI restAPI;

   public TwitterHandleFinder(UserRESTAPI rRestAPI) {
      this.restAPI = rRestAPI;
   }

   public PremisesEntity findAndInsertTwitterAccountForPremises(PremisesEntity premises) {
      UserAccountEntity userAccount = findTwitterAccountForPremises(premises);
      if (userAccount != null) {
         userAccount = DAO.saveOrUpdateUserAccount(userAccount);
         premises.getBelongToAgent().setUserAccount(userAccount);
         premises = DAO.saveOrUpdatePremises(premises);
      }
      return premises;
   }

   public UserAccountEntity findTwitterAccountForPremises(PremisesEntity premises) {
      UserAccountEntity userAccount = null;

      PlatformEntity platform = DAO.getPlatfromBasedOnName("Twitter");
      if (platform == null) {
         System.out.println("Twitter is not present in platforms, adding.");
         platform = new PlatformEntity("social network", "Twitter", "twitter.com");
         platform = DAO.insertPlatform(platform);
      }

      Set<User> responeseUsers = restAPI.searchUser(premises.getBusinessName());
      List<User> filteredUsers = new ArrayList<User>(filterUsersBasedOnLocation(responeseUsers, premises.getLocation().getAddress()));
 
      if (!filteredUsers.isEmpty()) {
         printPremiseInfo(premises);

         for (int i = 0; i < filteredUsers.size(); i++) {
            System.out.println(i + "\t: " + 
                  filteredUsers.get(i).getScreenName() + "  :  " + 
                  filteredUsers.get(i).getName() + "\t -\t " + 
                  filteredUsers.get(i).getLocation() + "\n\n" + 
                  filteredUsers.get(i).getDescription());
            System.out.println("\n---------------------------------------------\n");
         }

         boolean validInput = false;

         do {
            System.out.println("Enter the number of the associated twitter account, enter 'skip' to skip establishment or enter if 'none' if none of the accounts match the establishment: ");
            String input = System.console().readLine();

            if (input.isEmpty()) {
               System.out.println("Invalid input, try again.");
               validInput = false;
            } else if (input.equals("skip")) {
               userAccount = null;
               validInput = true;
               break;
            } else if (input.equals("none")) {
               userAccount = new UserAccountEntity();
               userAccount.setAgentId(premises.getBelongToAgent());
               userAccount.setLastCheckedDate(new Date());
               userAccount.setPlatformId(platform);
               validInput = true;
               break;
            }

            try {
               int userId = Integer.parseInt(input);
               if (userId > (filteredUsers.size() - 1)) {
                  System.out.println("Invalid input (ID not present), try again.");
                  validInput = false;
                  continue;
               }
               userAccount = new UserAccountEntity(filteredUsers.get(userId));
               userAccount.setAgentId(premises.getBelongToAgent());
               userAccount.setPlatformId(platform);
               validInput = true;
            } catch (NumberFormatException e) {
               System.out.println("Invalid input, try again.");
               validInput = false;
            }
         } while (!validInput);


         System.out.println("\n**********************************************************************\n");

      } else {
         // Same as none
         userAccount = new UserAccountEntity();
         userAccount.setAgentId(premises.getBelongToAgent());
         userAccount.setLastCheckedDate(new Date());
         userAccount.setPlatformId(platform);
      }
      return userAccount;
   }

   private List<User> filterUsersBasedOnLocation(Set<User> users, AddressEntity address) {
      List<User> filteredUsers = new ArrayList<User>();
      String userLocation = "";
      for (User user : users) {
         userLocation = user.getLocation();
         String[] establishmentAddressWords = address.getLine1().split(" ");

         if (userLocation.contains("UK") || userLocation.contains("United Kingdom")) {
            filteredUsers.add(user);
            continue;
         } else if (userLocation.contains(address.getCountry())) {
            filteredUsers.add(user);
            continue;
         } else if (userLocation.contains(address.getCity())) {
            filteredUsers.add(user);
            continue;
         } else if (userLocation.contains(address.getPostcode())) {
            filteredUsers.add(user);
            continue;
         }

         String currentWord = "";
         for (int j = 0; j < establishmentAddressWords.length; j++) {
            // make sure it is not checked against whitespace and empty
            currentWord = establishmentAddressWords[j].replaceAll("\\s+","");
            if (!currentWord.isEmpty() && userLocation.contains(currentWord)) {
               filteredUsers.add(user);
               break;
            }
         }

      }

      return filteredUsers;
   }

   private void printPremiseInfo(PremisesEntity premises) {
      System.out.println("Business Name: " + premises.getBusinessName());
      System.out.println("Business Type: " + premises.getBusinessType());
      System.out.println("Address: " + premises.getLocation().getDisplayString());
      if (premises.getBelongToAgent().getUserAccount() != null) {
         System.out.println("Current Handle: " + 
               premises.getBelongToAgent().getUserAccount().getDisplayName());
      }
   }
}
