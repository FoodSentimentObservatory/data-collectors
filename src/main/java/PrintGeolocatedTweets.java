import java.io.File;
import java.util.Set;

import ac.uk.abdn.foobs.Config;
import ac.uk.abdn.foobs.twitter.app.AppRESTAPI;
import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.Status;

/**
 * A utility / test class to get some Tweets from within a GeoCode and display
 * their text on the command line.
 * 
 * @author csc316
 *
 */
public class PrintGeolocatedTweets {

	public static void main(String[] args) {

		File file = new File("config.xml");

		Config config = new Config(file);

		AppRESTAPI api = new AppRESTAPI(config);
		// Scotland - rough approximation
		Set<Status> tweets = api.searchExactStringGeoCoded("food standards scotland", 200,
				new GeoLocation(56.496467, -3.801270), 177.312, Unit.km);

		// England - rough approximation

		// Set<Status> Tweets = api.searchExactStringGeoCoded("food standards
		// agency", 200, new GeoLocation(53.157312, -1.362305), 292.549,
		// Unit.km);

		// just printout out
		for (Status s : tweets) {
			System.out.println(s.getText());
		}

	}

}

