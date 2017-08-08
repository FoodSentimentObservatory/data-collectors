
package ac.uk.abdn.foobs.twitter.app;
/*
 * Author: Milan Markovic
 * 
 * 
 * 
 */


import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Table;

import ac.uk.abdn.foobs.db.entity.SearchDetailsEntity;

@Entity
@Table(name = "Search")
public class SearchObject extends SearchDetailsEntity{
	
	public Long id;
	
	public UUID uniqueID;
	public boolean completed =false;
	
	
	
	//default max value so we get the most recent tweet available - i.e. new search
	public long lastKonwnID=Long.MAX_VALUE;
	//optional set if some searches were were performed  previously 
		//and this will ensure that once teh ID is found in a search 
		//again the search will not go further in the past
	public long lastKonwnCachedID=0;
	
	public SearchObject () {
		this.uniqueID = UUID.randomUUID();
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public UUID getUniqueID() {
		return uniqueID;
	}


	public long getLastKonwnID() {
		return lastKonwnID;
	}


	public void setLastKonwnID(long lastKonwnID) {
		this.lastKonwnID = lastKonwnID;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public long getLastKonwnCachedID() {
		return lastKonwnCachedID;
	}


	public void setLastKonwnCachedID(long lastKonwnCachedID) {
		this.lastKonwnCachedID = lastKonwnCachedID;
	}


	
	

}
