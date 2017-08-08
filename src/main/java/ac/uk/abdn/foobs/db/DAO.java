package ac.uk.abdn.foobs.db;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ac.uk.abdn.foobs.Establishment;
import ac.uk.abdn.foobs.db.entity.AddressEntity;
import ac.uk.abdn.foobs.db.entity.AgentEntity;
import ac.uk.abdn.foobs.db.entity.GeoPointEntity;
import ac.uk.abdn.foobs.db.entity.LocationEntity;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.db.entity.PostEntity;
import ac.uk.abdn.foobs.db.entity.PremisesEntity;
import ac.uk.abdn.foobs.db.entity.RatingEntity;
import ac.uk.abdn.foobs.db.entity.SearchDetailsEntity;
import ac.uk.abdn.foobs.db.entity.UserAccountEntity;
import twitter4j.Status;

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
			address.setLine1(establishment.getAddress());
			if (establishment.getPostCode() != null) {
				address.setPostcode(establishment.getPostCode());
			}

			LocationEntity location = new LocationEntity();
			location.setAddressAndDispayString(address);

			address.setLocationId(location);

			if (establishment.getLocation() != null) {
				GeoPointEntity geoPoint = new GeoPointEntity(establishment.getLocation());
				location.setGeoPoint(geoPoint);
				geoPoint.setLocationId(location);
				session.save(geoPoint);
			} else {
				location.setGeoPoint(null);
			}

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

			if (establishment.getTwitterHandle() == null) {

			} else if (establishment.getTwitterHandle().equals("NONE")) {
				UserAccountEntity userAccount = new UserAccountEntity();
				userAccount.setAgentId(agent);
				userAccount.setLastCheckedDate(new Date());
				userAccount.setPlatformId(platform);
				agent.setUserAccount(userAccount);
				session.save(userAccount);
			} else {
				UserAccountEntity userAccount = new UserAccountEntity(establishment.getTwitter());
				userAccount.setAgentId(agent);
				userAccount.setPlatformId(platform);

				agent.setUserAccount(userAccount);
				session.save(userAccount);
			}

			session.save(rating);
			session.save(premises);
			session.save(address);
			session.save(location);
			session.save(agent);

			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println();
			System.out.println();
			System.out.println(establishment.getBusinessName());
			System.out.println();
			System.out.println();
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	public static void saveTweet(UserAccountEntity user, Status tweet, SearchDetailsEntity searchDetails) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
        	 PostEntity post = new PostEntity(tweet);
        	 if (post.getLocationId()!=null) {
        		 session.saveOrUpdate(post.getLocationId());
        		 session.saveOrUpdate(post.getLocationId().getGeoPoint());
        	 }
                    post.setHasCreator(user);
                    post.setSearchDetailsId(searchDetails);
                    session.saveOrUpdate(post);
                    session.getTransaction().commit();
        } catch (Exception e) {
                    transaction.rollback();
                    e.printStackTrace();
        } finally {
                    session.close();
        }
}
	public static void saveSearchDetails(SearchDetailsEntity searchDetails){
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				Transaction transaction = session.beginTransaction();
		
				try {
					session.saveOrUpdate(searchDetails.getLocationId());
					session.saveOrUpdate(searchDetails.getLocationId().getGeoPoint());
					session.saveOrUpdate("ac.uk.abdn.foobs.db.entity.SearchDetailsEntity",searchDetails);
					session.getTransaction().commit();
				} catch (Exception e) {
					transaction.rollback();
					e.printStackTrace();
				} finally {
					session.close();
				}
			}

	public static UserAccountEntity getUserAccountByIdAndPlatform(String platformAccountId,
			PlatformEntity platformEntity) {
		UserAccountEntity userAccount = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from UserAccountEntity uae where uae.platformAccountId=:paid and uae.platformId=:pid";
		try {

			List<UserAccountEntity> results = session.createQuery(hql, UserAccountEntity.class)
					.setParameter("paid", platformAccountId)
					.setParameter("pid", platformEntity)
					.getResultList();
			if (results.size() > 0) {
				userAccount = results.get(0);
			}
		} finally {
			session.close();
		}
		return userAccount;

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

	public static Set<PremisesEntity> getPremisesWithUncheckedUserAccount(String accountName) {
		Set<PremisesEntity> premisesSet = new HashSet<PremisesEntity>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from PremisesEntity as premises " + "where not exists (" + "from UserAccountEntity as account "
				+ "where account.agentId = premises.belongToAgent " + "and account.platformId = ("
				+ "select platform.Id " + "from PlatformEntity as platform " + "where platform.forumName=:name))";
		try {
			List<PremisesEntity> results = session.createQuery(hql, PremisesEntity.class)
					.setParameter("name", accountName).getResultList();
			premisesSet.addAll(results);
		} finally {
			session.close();
		}
		return premisesSet;
	}

	public static PlatformEntity getPlatfromBasedOnName(String name) {
		PlatformEntity platform = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		String hql = "from PlatformEntity platform where platform.forumName=:name";
		try {
			List<PlatformEntity> results = session.createQuery(hql, PlatformEntity.class).setParameter("name", name)
					.getResultList();
			if (results.size() > 0) {
				platform = results.get(0);
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
			premises = (PremisesEntity) session.get(PremisesEntity.class, id);
		} finally {
			session.close();
		}

		return premises;
	}

	public static Set<UserAccountEntity> getTwitterAccounts() {
		Set<UserAccountEntity> userAccountSet = new HashSet<UserAccountEntity>();

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String hql = "from UserAccountEntity user " + "where user.platformAccountId is not null";
		try {
			List<UserAccountEntity> results = session.createQuery(hql, UserAccountEntity.class).getResultList();
			userAccountSet.addAll(results);
		} finally {
			session.close();
		}
		return userAccountSet;
	}

	public static void saveTweet(UserAccountEntity user, Status tweet) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();

		try {
			PostEntity post = new PostEntity(tweet);
			post.setHasCreator(user);
			session.saveOrUpdate(post);
			session.getTransaction().commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
