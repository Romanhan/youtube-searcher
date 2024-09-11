package eu.romanhan.youtube;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YoutubeSearcher {

	private static Logger logger = LoggerFactory.getLogger(YoutubeSearcher.class);
	private static String API_KEY;
	private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/search";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public YoutubeSearcher() {
		Properties properties = new Properties();
		try (InputStream input = YoutubeSearcher.class.getClassLoader().getResourceAsStream("config.properties")) {
			properties.load(input);
			API_KEY = properties.getProperty("youtube.api.key");
		} catch (IOException e) {
			logger.error("Failed to load API_KEY");
		}
	}

	// Getter for testing purpose
	protected String getApiKey() {
		return API_KEY;
	}

}
