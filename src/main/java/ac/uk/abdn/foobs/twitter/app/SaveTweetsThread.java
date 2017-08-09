package ac.uk.abdn.foobs.twitter.app;

import java.util.Set;

import org.hibernate.Session;

import ac.uk.abdn.foobs.db.DAO;
import ac.uk.abdn.foobs.db.HibernateUtil;
import ac.uk.abdn.foobs.db.entity.AgentEntity;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.db.entity.SearchDetailsEntity;
import ac.uk.abdn.foobs.db.entity.UserAccountEntity;
import twitter4j.Status;

public class SaveTweetsThread extends Thread {
	Set <Status> chunk;
	PlatformEntity twitter;
	SearchDetailsEntity searchDetails;
	
    SaveTweetsThread( Set <Status> chunk,PlatformEntity twitter,SearchDetailsEntity searchDetails) {
        this.chunk = chunk;
        this.twitter=twitter;
        this.searchDetails=searchDetails;
    }

    public void run() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
		try {
		for (Status chunk_tweet : chunk) {

			// create a UserAccountEntity for the Status user to ensure
			// the
			// correct
			// platformAccountId is used as part of the DB lookup.
			UserAccountEntity basicUser = new UserAccountEntity(chunk_tweet.getUser());
			UserAccountEntity dbUser = DAO.getUserAccountByIdAndPlatformMutithread(session,
					basicUser.getPlatformAccountId(), twitter);
			if (dbUser != null) {
				// already have this user in the DB
				basicUser = dbUser;
				// TODO: This will not overwrite the existing record of
				// the user
				// with any changes they have made,
				// details from their profile was stored in the DB
			} else {
				// new user to the system, so initialise it
				basicUser.setPlatformId(twitter);
				AgentEntity agent = new AgentEntity();
				agent.setAgentType("Person");
				basicUser.setAgentId(agent);
			}
			basicUser = DAO.saveOrUpdateUserAccountMultithread(session, basicUser);
			DAO.saveTweet(basicUser, chunk_tweet, searchDetails);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.print(".");
			session.close();
		}
    }
}