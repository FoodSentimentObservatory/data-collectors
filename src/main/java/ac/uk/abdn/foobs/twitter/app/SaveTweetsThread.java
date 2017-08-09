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
    	
		
		for (Status chunk_tweet : chunk) {

			
			
			DAO.saveTweetMultithread(chunk_tweet,searchDetails,twitter);
		
		
			
			
		}
		System.out.print("*");
    }
}