package ac.uk.abdn.foobs.db;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.db.entity.AddressEntity;
import ac.uk.abdn.foobs.db.entity.AgentEntity;
import ac.uk.abdn.foobs.db.entity.LocationEntity;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.db.entity.PremisesEntity;
import ac.uk.abdn.foobs.db.entity.RatingEntity;
import ac.uk.abdn.foobs.db.entity.UserAccountEntity;

public class DAO {
   
   public static void insertEstablishment(Establishment establishment, String country) {
      if (getPremises(establishment.getFHRSID()) != null) {
         System.out.println("Establishment alrady in database, will not save");
         return;
      }

      PlatformEntity platform = getPlatfromBasedOnName("Twitter");
      if (platform == null) {
         System.out.println("Could not find platform, will not insert Establishment");
         return;
      }

      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();

      try {

         AddressEntity address = new AddressEntity();
         address.setCountry(country);
         address.setCity(establishment.getCity());
         address.setPostcode(establishment.getPostCode());
         address.setLine1(establishment.getAddress());

         LocationEntity location = new LocationEntity();
         location.setAddressAndDispayString(address);

         address.setLocationId(location);

         AgentEntity agent = new AgentEntity();
         agent.setAgentType("Premises");

         PremisesEntity premises = new PremisesEntity();
         premises.setId(establishment.getFHRSID());
         premises.setBusinessName(establishment.getBusinessName());
         premises.setBusinessType(establishment.getBusinessType());
         premises.setBelongToAgent(agent);
         premises.setLocation(location);

         RatingEntity rating = new RatingEntity();
         rating.setNewRatingPending(establishment.getNewRatingPending());
         rating.setRatingDate(establishment.getRatingDate());
         rating.setRatingKey(establishment.getRatingKey());
         rating.setRatingValue(establishment.getRatingValue());
         rating.setSchemeType(establishment.getSchemeType());
         rating.setPremisesId(premises);

         if (establishment.getTwitterHandle().equals("NONE")) {
            UserAccountEntity userAccount = new UserAccountEntity();
            userAccount.setAgentId(agent);
            userAccount.setLastCheckedDate(new Date());
            userAccount.setPlatformId(platform);
            session.save(userAccount);
         } else if (establishment.getTwitterHandle() != null) {
            UserAccountEntity userAccount = new UserAccountEntity();
            userAccount.setAgentId(agent);
            userAccount.setLastCheckedDate(new Date());
            userAccount.setPlatformId(platform);

            userAccount.setAccountCreatedAt(establishment.getTwitter().getCreatedAt());
            userAccount.setAccountURL(establishment.getTwitter().getURL());
            userAccount.setDisplayName(establishment.getTwitter().getScreenName());
            userAccount.setPlatformAccountId(establishment.getTwitterHandle());
            userAccount.setProfileDescription(establishment.getTwitter().getDescription());
            userAccount.setVerified(establishment.getTwitter().isVerified());
            session.save(userAccount);
         }

         session.save(rating);
         session.save(location);
         session.save(address);
         session.save(agent);
         session.save(premises);

         session.getTransaction().commit();
      } catch (Exception e) {
         transaction.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }

   public static PlatformEntity getPlatfromBasedOnName(String name) {
      PlatformEntity platform = null;
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();
      String hql = "from PlatformEntity platform where platform.forumName LIKE '%"+name+"%'";
      try {
         List results = session.createQuery(hql).getResultList();
         if (results.size() > 0) {
            platform = (PlatformEntity)results.get(0);
         }
      } finally {
         session.close();
      }
      return platform;
   }

   public static PremisesEntity getPremises(Integer id) {
      PremisesEntity premises = null;
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();

      try {
         premises = (PremisesEntity)session.get(PremisesEntity.class,id);
      } finally {
         session.close();
      }

      return premises;
   }
}
