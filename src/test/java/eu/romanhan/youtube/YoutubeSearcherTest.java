package eu.romanhan.youtube;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
