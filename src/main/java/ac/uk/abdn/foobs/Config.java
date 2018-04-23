package ac.uk.abdn.foobs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import twitter4j.GeoLocation;
import ac.uk.abdn.foobs.utils.XMLUtils;

//NOTE - IMPORTANT all this needs to be refactored and handled better for repetitive seaerches etc. 
//URRENTLY there is only a support for one newsArticle search

public class Config {
	private String twitterAppConsumerKey;
	private String twitterAppConsumerSecret;
	private String twitterUserConsumerKey;
	private String twitterUserConsumerSecret;
	private String twitterUserAccessToken;
	private String twitterUserAccessTokenSecret;
	private String dbConnectionUrl;
	private String ratingFile;
	private String ratingFileCountry;
	private String keywordsFilename;
	private String keywordsFileEncoding;
	private Integer parseAndUploadRatings;
	private Integer findTwitterAccounts;
	private Integer findTweetsFromRestaurants;
	private Integer findTweetsContainingKeywords;
	private int getNewsArticles;
	/// private ArrayList <String> geoPoint;
	/// private String radius;
	/// private String note;
	/// private String unit;

	private String newsArticleSearchUnit;

	public String getNewsArticleSearchUnit() {
		return newsArticleSearchUnit;
	}

	public void setNewsArticleSearchUnit(String newsArticleSearchUnit) {
		this.newsArticleSearchUnit = newsArticleSearchUnit;
	}

	private String newsArticleSearchLongitude;

	public String getNewsArticleSearchLongitude() {
		return newsArticleSearchLongitude;
	}

	public void setNewsArticleSearchLongitude(String newsArticleSearchLongitude) {
		this.newsArticleSearchLongitude = newsArticleSearchLongitude;
	}

	private String newsArticleSearchLatitude;

	public String getNewsArticleSearchLatitude() {
		return newsArticleSearchLatitude;
	}

	public void setNewsArticleSearchLatitude(String newsArticleSearchLatitude) {
		this.newsArticleSearchLatitude = newsArticleSearchLatitude;
	}

	private String newsArticleSearchKeywords;

	public String getNewsArticleSearchKeywords() {
		return newsArticleSearchKeywords;
	}

	public void setNewsArticleSearchKeywords(String newsArticleSearchKeywords) {
		this.newsArticleSearchKeywords = newsArticleSearchKeywords;
	}

	private String newsArticleSearchRadius;

	public String getNewsArticleSearchRadius() {
		return newsArticleSearchRadius;
	}

	public void setNewsArticleSearchRadius(String newsArticleSearchRadius) {
		this.newsArticleSearchRadius = newsArticleSearchRadius;
	}

	private String newsArticleSearchNote;

	public String getNewsArticleSearchNote() {
		return newsArticleSearchNote;
	}

	public void setNewsArticleSearchNote(String newsArticleSearchNote) {
		this.newsArticleSearchNote = newsArticleSearchNote;
	}

	private Integer searchLength;
	List<String> latitude = new ArrayList<>();
	List<String> longitude = new ArrayList<>();
	List<String> radius = new ArrayList<>();

	// NOTE: WE ARE CURRENTLY USING THIS AS A UNIQUE IDENTIFIER OF THE SEARCH
	List<String> note = new ArrayList<>();
	List<String> unit = new ArrayList<>();
	HashMap<String, ArrayList<Long>> previousTweetIDs = new HashMap<String, ArrayList<Long>>();
	ArrayList<List<String>> keywordListsForSearches = new ArrayList<>();

	public Config(File file) {
		readAndSetConfig(file);
	}

	// chnage added by Nikol
	private void readAndSetConfig(File file) {
		Document document = XMLUtils.readXml(file);

		if (document == null) {
			System.out.println("Failed to read config file");
			return;
		}

		Element rootElement = document.getDocumentElement();

		Element twitterApp = (Element) rootElement.getElementsByTagName("TwitterApp").item(0);

		this.twitterAppConsumerKey = XMLUtils.getStringValue(twitterApp, "ConsumerKey");
		this.twitterAppConsumerSecret = XMLUtils.getStringValue(twitterApp, "ConsumerSecret");

		Element twitterUser = (Element) rootElement.getElementsByTagName("TwitterUser").item(0);

		this.twitterUserConsumerKey = XMLUtils.getStringValue(twitterUser, "ConsumerKey");
		this.twitterUserConsumerSecret = XMLUtils.getStringValue(twitterUser, "ConsumerSecret");
		this.twitterUserAccessToken = XMLUtils.getStringValue(twitterUser, "UserAccessToken");
		this.twitterUserAccessTokenSecret = XMLUtils.getStringValue(twitterUser, "UserAccessTokenSecret");

		Element db = (Element) rootElement.getElementsByTagName("Db").item(0);
		this.dbConnectionUrl = XMLUtils.getStringValue(db, "ConnectionUrl");
		
		//handle newsPAperSearch elements
		Element newsArticleSearch = (Element) rootElement.getElementsByTagName("NewsArticleSearch").item(0);
		this.newsArticleSearchKeywords= XMLUtils.getStringValue(newsArticleSearch, "NewsArticleSearchKeywords");
		this.newsArticleSearchLatitude= XMLUtils.getStringValue(newsArticleSearch, "NewsArticleSearchLatitude");
		this.newsArticleSearchLongitude= XMLUtils.getStringValue(newsArticleSearch, "NewsArticleSearchLongitude");
		this.newsArticleSearchNote= XMLUtils.getStringValue(newsArticleSearch, "NewsArticleSearchNote");
		this.newsArticleSearchRadius= XMLUtils.getStringValue(newsArticleSearch, "NewsArticleSearchRadius");
		this.newsArticleSearchUnit = XMLUtils.getStringValue(newsArticleSearch, "NewsArticleSearchUnit");

		Element rating = (Element) rootElement.getElementsByTagName("Rating").item(0);
		this.ratingFile = XMLUtils.getStringValue(rating, "RatingFile");
		this.ratingFileCountry = XMLUtils.getStringValue(rating, "Country");

		Element tasks = (Element) rootElement.getElementsByTagName("Tasks").item(0);
		this.parseAndUploadRatings = XMLUtils.getIntValue(tasks, "ParseAndUploadRatings");
		this.findTwitterAccounts = XMLUtils.getIntValue(tasks, "FindTwitterAccounts");
		this.findTweetsFromRestaurants = XMLUtils.getIntValue(tasks, "FindTweetsFromRestaurants");
		this.findTweetsContainingKeywords = XMLUtils.getIntValue(tasks, "FindTweetsContainingKeywords");
		this.getNewsArticles = XMLUtils.getIntValue(tasks, "GetNewsArticles");

		Element keywordsFile = (Element) rootElement.getElementsByTagName("KeywordsFile").item(0);
		this.keywordsFilename = XMLUtils.getStringValue(keywordsFile, "Filename");
		this.keywordsFileEncoding = XMLUtils.getStringValue(keywordsFile, "FileEncoding");

		/// generates a list of all nodes with that tag, aka all searches
		NodeList searches = rootElement.getElementsByTagName("Search");
		searchLength = searches.getLength();
		/// looping through each of the searches and adding the values to the relevant
		/// lists, which are then used by the functions
		for (int temp = 0; temp < searches.getLength(); temp++) {
			Node nNode = (Node) searches.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String latitudeS = XMLUtils.getStringValue(eElement, "Latitude");
				String longitudeS = XMLUtils.getStringValue(eElement, "Longitude");
				String radiusS = XMLUtils.getStringValue(eElement, "Radius");
				String noteS = XMLUtils.getStringValue(eElement, "Note");
				String unitS = XMLUtils.getStringValue(eElement, "Unit");
				String kFileName = XMLUtils.getStringValue(eElement, "Filename");
				String kFileEncoding = XMLUtils.getStringValue(eElement, "FileEncoding");
				List<String> fileData = new ArrayList<>();
				fileData.add(kFileName);
				fileData.add(kFileEncoding);
				keywordListsForSearches.add(fileData);
				latitude.add(latitudeS);
				longitude.add(longitudeS);
				radius.add(radiusS);
				// ADDED UUID to note just to make sure that note is always inserted and unique
				// - this will be however shared for all the split searches from the same group
				// - > they will have doifferent search id in the database though

				String noteString = noteS;
				note.add(noteString);
				unit.add(unitS);
				NodeList previousIDs = rootElement.getElementsByTagName("FirstTweetIDsFromPreviousSearch");

				ArrayList<Long> list = new ArrayList<Long>();
				for (int i = 0; i < previousIDs.getLength(); i++) {
					nNode = (Node) previousIDs.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						if (XMLUtils.getStringValue(eElement, "ID") != null) {
							list.add(Long.parseLong(XMLUtils.getStringValue(eElement, "ID")));
						}
					}
				}

				previousTweetIDs.put(noteString, list);

			}
		}
	}

	/// method to get the amount of searches which can be used to create a forloop
	/// and access the relevant values
	public int getSearchLength() {
		return searchLength;
	}

	public HashMap<String, ArrayList<Long>> getPreviousTweetIDsForIndividualSearches() {
		return previousTweetIDs;
	}

	public ArrayList<List<String>> getFileData() {
		return keywordListsForSearches;
	}

	/// return lists of all GeoPoints from the config file
	public List<String> getLatitude() {
		return latitude;
	}

	public List<String> getLongitude() {
		return longitude;
	}

	/// returns a list of all radiuses
	public List<String> getRadius() {
		return radius;
	}

	/// returns a list of all units
	public List<String> getUnit() {
		return unit;
	}

	/// returns a list of all notes
	public List<String> getNote() {
		return note;
	}

	/**
	 * @return the twitterAppConsumerKey
	 */
	public String getTwitterAppConsumerKey() {
		return twitterAppConsumerKey;
	}

	/**
	 * @return the twitterAppConsumerSecret
	 */
	public String getTwitterAppConsumerSecret() {
		return twitterAppConsumerSecret;
	}

	/**
	 * @return the twitterUserConsumerKey
	 */
	public String getTwitterUserConsumerKey() {
		return twitterUserConsumerKey;
	}

	/**
	 * @return the twitterUserConsumerSecret
	 */
	public String getTwitterUserConsumerSecret() {
		return twitterUserConsumerSecret;
	}

	/**
	 * @return the twitterUserAccessToken
	 */
	public String getTwitterUserAccessToken() {
		return twitterUserAccessToken;
	}

	/**
	 * @return the twitterUserAccessTokenSecret
	 */
	public String getTwitterUserAccessTokenSecret() {
		return twitterUserAccessTokenSecret;
	}

	/**
	 * @return the dbConnectionUrl
	 */
	public String getDbConnectionUrl() {
		return dbConnectionUrl;
	}

	/**
	 * @return the ratingFile
	 */
	public String getRatingFile() {
		return ratingFile;
	}

	/**
	 * @param ratingFile
	 *            the ratingFile to set
	 */
	public void setRatingFile(String ratingFile) {
		this.ratingFile = ratingFile;
	}

	/**
	 * @return the ratingFileCountry
	 */
	public String getRatingFileCountry() {
		return ratingFileCountry;
	}

	/**
	 * @param ratingFileCountry
	 *            the ratingFileCountry to set
	 */
	public void setRatingFileCountry(String ratingFileCountry) {
		this.ratingFileCountry = ratingFileCountry;
	}

	/**
	 * @return the parseAndUploadRatings
	 */
	public Integer getParseAndUploadRatings() {
		return parseAndUploadRatings;
	}

	/**
	 * @param parseAndUploadRatings
	 *            the parseAndUploadRatings to set
	 */
	public void setParseAndUploadRatings(Integer parseAndUploadRatings) {
		this.parseAndUploadRatings = parseAndUploadRatings;
	}

	/**
	 * @return the findTwitterAccounts
	 */
	public Integer getFindTwitterAccounts() {
		return findTwitterAccounts;
	}

	/**
	 * @param findTwitterAccounts
	 *            the findTwitterAccounts to set
	 */
	public void setFindTwitterAccounts(Integer findTwitterAccounts) {
		this.findTwitterAccounts = findTwitterAccounts;
	}

	/**
	 * @return the findTweetsFromRestaurants
	 */
	public Integer getFindTweetsFromRestaurants() {
		return findTweetsFromRestaurants;
	}

	/**
	 * @param findTweetsFromRestaurants
	 *            the findTweetsFromRestaurants to set
	 */
	public void setFindTweetsFromRestaurants(Integer findTweetsFromRestaurants) {
		this.findTweetsFromRestaurants = findTweetsFromRestaurants;
	}

	public String getKeywordsFilename() {
		return keywordsFilename;
	}

	public String getKeywordsFileEncoding() {
		return keywordsFileEncoding;
	}

	public Integer getFindTweetsContainingKeywords() {
		return findTweetsContainingKeywords;
	}

	public int getNewsArticles() {
		// TODO Auto-generated method stub
		return getNewsArticles;
	}

}
