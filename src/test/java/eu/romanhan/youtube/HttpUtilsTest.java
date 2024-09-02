package eu.romanhan.youtube;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class HttpUtilsTest {

	@Test
	void testSendGetRequest() throws IOException {
		// Arrange
		String testUrl = "http://example.com";
		String expectedResponse = "Hello, World!";

		HttpURLConnection mockConnection = mock(HttpURLConnection.class);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(expectedResponse.getBytes());
		when(mockConnection.getInputStream()).thenReturn(inputStream);

		try (MockedStatic<HttpUtils> mockedHttpUtils = mockStatic(HttpUtils.class)) {
			// Mock the static createConnection method
			mockedHttpUtils.when(() -> HttpUtils.createConnection(testUrl)).thenReturn(mockConnection);

			// Call the real sendGetRequest method
			mockedHttpUtils.when(() -> HttpUtils.sendGetRequest(testUrl)).thenCallRealMethod();

			// Act
			String result = HttpUtils.sendGetRequest(testUrl);

			// Assert
			assertEquals(expectedResponse, result);
			verify(mockConnection).setRequestMethod("GET");
			verify(mockConnection).disconnect();

			// Verify that createConnection was called with the correct URL
			mockedHttpUtils.verify(() -> HttpUtils.createConnection(testUrl));
		}
	}

}
