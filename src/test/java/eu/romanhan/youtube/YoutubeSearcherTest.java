package eu.romanhan.youtube;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class YoutubeSearcherTest {

	@Test
	void testApiKeyLoadingSuccess() throws IOException {
		YoutubeSearcher youtubeSearcher = new YoutubeSearcher();
		String apiKey = youtubeSearcher.getApiKey();

		assertNotNull(apiKey, "API key should not be null");
		assertFalse(apiKey.isEmpty());
		assertTrue(apiKey.length() > 0 && apiKey.length() < 40);
	}

	@Test
	void testApiKeyLoadingError() throws IOException {
		YoutubeSearcher youtubeSearcher = new YoutubeSearcher();
		String apiKey = youtubeSearcher.getApiKey() + "error";

		assertNotEquals(apiKey, youtubeSearcher.getApiKey());
	}
}
