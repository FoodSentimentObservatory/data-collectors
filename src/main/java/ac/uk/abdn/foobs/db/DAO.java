package ac.uk.abdn.foobs.db;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.db.entity.AddressEntity;
import ac.uk.abdn.foobs.db.entity.AgentEntity;
import ac.uk.abdn.foobs.db.entity.GeoPointEntity;
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
         System.out.println("Twitter is not present in platforms, adding.");
         platform = new PlatformEntity("social network", "Twitter", "twitter.com");
         platform = insertPlatform(platform);
      }

      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();

      try {

         AddressEntity address = new AddressEntity();
         address.setCountry(country);
         address.setCity(establishment.getCity());
         address.setPostcode(establishment.getPostCode());
         address.setLine1(establishment.getAddress());

         GeoPointEntity geoPoint = new GeoPointEntity(establishment.getLocation());

         LocationEntity location = new LocationEntity();
         location.setAddressAndDispayString(address);
         location.setGeoPoint(geoPoint);

         address.setLocationId(location);
         geoPoint.setLocationId(location);

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
         premises.getRatings().add(rating);

         if (establishment.getTwitterHandle().equals("NONE")) {
            UserAccountEntity userAccount = new UserAccountEntity();
            userAccount.setAgentId(agent);
            userAccount.setLastCheckedDate(new Date());
            userAccount.setPlatformId(platform);
            agent.setUserAccount(userAccount);
            session.save(userAccount);
         } else if (establishment.getTwitterHandle() != null) {
            UserAccountEntity userAccount = new UserAccountEntity(establishment.getTwitter());
            userAccount.setAgentId(agent);
            userAccount.setPlatformId(platform);

            agent.setUserAccount(userAccount);
            session.save(userAccount);
         }

         session.save(rating);
         session.save(premises);
         session.save(address);
         session.save(geoPoint);
         session.save(location);
         session.save(agent);

         session.getTransaction().commit();
      } catch (Exception e) {
         transaction.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }

   public static UserAccountEntity saveOrUpdateUserAccount(UserAccountEntity userAccount) {

      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();

      try {
         session.saveOrUpdate(userAccount);
         session.getTransaction().commit();
      } catch (Exception e) {
         transaction.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return userAccount;
   }

   public static PremisesEntity saveOrUpdatePremises(PremisesEntity premises) {

      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();

      try {
         session.saveOrUpdate(premises);
         session.getTransaction().commit();
      } catch (Exception e) {
         transaction.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return premises;
   }

   public static PlatformEntity insertPlatform(PlatformEntity platform) {

      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      Transaction transaction = session.beginTransaction();

      try {
         session.saveOrUpdate(platform);
         session.getTransaction().commit();
      } catch (Exception e) {
         transaction.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
      return platform;
   }

   public static PlatformEntity getPlatfromBasedOnName(String name) {
      PlatformEntity platform = null;
      Session session = HibernateUtil.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      String hql = "from PlatformEntity platform where platform.forumName=:name";
      try {
         List results = session.createQuery(hql)
                           .setParameter("name", name)
                           .getResultList();
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
      session.beginTransaction();

      try {
         premises = (PremisesEntity)session.get(PremisesEntity.class,id);
      } finally {
         session.close();
      }

      return premises;
   }
}
