package ac.uk.abdn.foobs.twitter.app;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
	SessionFactory factory;
	
    SaveTweetsThread( Set <Status> chunk,PlatformEntity twitter,SearchDetailsEntity searchDetails, SessionFactory factory) {
        this.chunk = chunk;
        this.twitter=twitter;
        this.searchDetails=searchDetails;
        this.factory=factory;
    }

    public void run() {
    	
		
		for (Status chunk_tweet : chunk) {

			
			
			DAO.saveTweetMultithread(chunk_tweet,searchDetails,twitter, factory);
		
		
			
			
		}
		System.out.print("*");
    }
}