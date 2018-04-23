package ac.uk.abdn.foobs.newsarticles.app;

import java.util.ArrayList;
import java.util.Date;

import ac.uk.abdn.foobs.db.DAO;
import ac.uk.abdn.foobs.db.entity.PlatformEntity;
import ac.uk.abdn.foobs.twitter.app.SearchObject;

public class SaveArticles {

	public static void saveArticlesToDB (ArrayList articles, SearchObject searchDetails) {
		for (int i =0; i<articles.size();i++) {
			
			String platformName = (((ArrayList) articles.get(i)).get(0)).toString();
			String articleBody = (((ArrayList) articles.get(i)).get(2)).toString();
			//TO do parse date
	
			NewsArticle article = new NewsArticle (articleBody, (Date) ((ArrayList) articles.get(i)).get(1),platformName,searchDetails );
			
			DAO.saveNewsArticle(article);
		}
	}
	
}
