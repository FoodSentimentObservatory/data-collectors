package ac.uk.abdn.foobs.newsarticles.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class ParseArticles {
	private static String FILENAME = "./resources/newsarticles_lexis/out.txt";

	public static ArrayList parse (String pathToFileContainingArticles) {
		BufferedReader br = null;
		FileReader fr = null;

		int counter = 0;
		boolean inArticleBody = false;
		boolean inArticleHead = true;
		boolean inArticleMeataData = false;
		ArrayList article = new ArrayList();
		ArrayList articles = new ArrayList();
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;
			String body = "";

			while ((sCurrentLine = br.readLine()) != null) {

				if (inArticleHead) {

					if (sCurrentLine.contains("All Rights Reserved")) {
						inArticleMeataData = true;
						continue;
					}
				}

				if (inArticleHead && sCurrentLine.contains("HEADLINE:")) {
					inArticleHead = false;
					inArticleMeataData = false;

				}

				if (inArticleMeataData) {
					if (!sCurrentLine.equals("") && article.size() < 1) {
						article.add(sCurrentLine.trim());
					}
				}

				// catch the start of bad review
				if (sCurrentLine.contains("BODY:")) {
					inArticleBody = true;
					continue;
				}

				if (sCurrentLine.contains("LOAD-DATE:")) {
                    
					String date = sCurrentLine.replace("LOAD-DATE:", "");
					//System.out.println (date);
					
					DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					Date parsedDate = df.parse(date.trim());
					
					article.add(parsedDate);
					article.add(body);
					articles.add(article);
					article = new ArrayList();
					body = "";
					inArticleBody = false;
					inArticleHead = true;
				}

				// catch the review text
				if (!sCurrentLine.equals("") && inArticleBody) {
					// System.out.println(sCurrentLine);
					body = body + " " + sCurrentLine;

					counter++;
				}

			}

		} catch (IOException e) {

			e.printStackTrace();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		// System.out.println(articles);
		System.out.println("Parsed articles count: " + articles.size());

		ArrayList cleanedArticles = new ArrayList();

		// get rid of those where we failed to extract all the info - platform, date ,
		// body
		for (int i = 0; i < articles.size(); i++) {
			if (((ArrayList) articles.get(i)).size() == 3) {
				cleanedArticles.add(articles.get(i));
			}
		}

		System.out.println("Cleaned articles count: " + cleanedArticles.size());
		return cleanedArticles;

	}
}
