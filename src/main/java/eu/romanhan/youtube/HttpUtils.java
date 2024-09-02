package eu.romanhan.youtube;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

	// This method is added to make the class more testable
	protected static HttpURLConnection createConnection(String urlString) throws IOException {
		URL url = new URL(urlString);
		return (HttpURLConnection) url.openConnection();
	}

	public static String sendGetRequest(String urlString) throws IOException {
		HttpURLConnection connection = createConnection(urlString);
		connection.setRequestMethod("GET");
		StringBuilder response = new StringBuilder();

		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		} finally {
			connection.disconnect();
		}
		return response.toString();
	}
}
