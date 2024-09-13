package eu.romanhan.youtube;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class YoutubeSearcher {

	private static Logger logger = LoggerFactory.getLogger(YoutubeSearcher.class);
	private static String API_KEY;
	private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/search";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

	static {
		Properties properties = new Properties();
		try (InputStream input = YoutubeSearcher.class.getClassLoader().getResourceAsStream("config.properties")) {
			properties.load(input);
			API_KEY = properties.getProperty("youtube.api.key");
			if (API_KEY == null || API_KEY.isEmpty()) {
				logger.error("API_KEY is null or empty");
			}
		} catch (IOException e) {
			logger.error("Failed to load API_KEY");
		}
	}

	// Getter for testing purpose
	protected String getApiKey() {
		return API_KEY;
	}

	protected List<Video> searchYouTubeVideos(String query, LocalDate startDate, LocalDate endDate)
			throws IOException, URISyntaxException {
		String jsonResponse = fetchYouTubeData(query);
		return extractVideoInfo(jsonResponse, startDate, endDate);
	}

	private String fetchYouTubeData(String query) throws IOException, URISyntaxException {
		String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
		String urlString = String.format("%s?part=snippet&q=%s&type=video&key=%s&maxResults=50", BASE_URL, encodedQuery,
				getApiKey());

		URI uri = new URI(urlString);

		return HttpUtils.sendGetRequest(uri.toString());
	}

	private List<Video> extractVideoInfo(String jsonResponse, LocalDate startDate, LocalDate endDate) {
		JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
		JsonArray items = jsonObject.getAsJsonArray("items");

		List<Video> videos = new ArrayList<>();

		for (var itemElement : items) {
			JsonObject item = itemElement.getAsJsonObject();
			JsonObject snippet = item.getAsJsonObject("snippet");

			LocalDate publishDate = LocalDate.parse(snippet.get("publishedAt").getAsString().split("T")[0],
					dateFormatter);

			if (isWithinDateRange(publishDate, startDate, endDate)) {
				videos.add(createVideo(item, snippet));
			}
		}
		return videos;
	}

	private Video createVideo(JsonObject item, JsonObject snippet) {
		String videoId = item.getAsJsonObject("id").get("videoId").getAsString();
		String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
		String title = snippet.get("title").getAsString();
		return new Video(videoUrl, title);
	}

	private boolean isWithinDateRange(LocalDate publishDate, LocalDate startDate, LocalDate endDate) {
		return !publishDate.isBefore(startDate) && !publishDate.isAfter(endDate);
	}

}
