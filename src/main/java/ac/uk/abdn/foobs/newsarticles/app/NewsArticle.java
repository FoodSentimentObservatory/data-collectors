package ac.uk.abdn.foobs.newsarticles.app;

import java.util.Date;

import ac.uk.abdn.foobs.db.entity.SearchDetailsEntity;

public class NewsArticle {

	private Date createdAt;
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	private String body;
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	private String platform;
	private SearchDetailsEntity searchDetailsEntity;
	
	public SearchDetailsEntity getSearchDetailsEntity() {
		return searchDetailsEntity;
	}

	public void setSearchDetailsEntity(SearchDetailsEntity searchDetailsEntity) {
		this.searchDetailsEntity = searchDetailsEntity;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public NewsArticle (String body, Date createdAt,String platform, SearchDetailsEntity searchDetailsEntity) {
		this.createdAt =createdAt;
		this.body = body;
		this.platform = platform;
		this.searchDetailsEntity = searchDetailsEntity;
		
		//we dont have enough details about the user that created the article so we assume that the platform name is used to also create an agent - publisher - whihc will have a corresponding account. This means that all articles from the same platform (e.g. Times ) will be associated with the same agent and usr account.
	    
	
	
	}
}
