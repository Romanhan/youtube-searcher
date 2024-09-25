package eu.romanhan.youtube;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter search request:");
			String searchQuery = scanner.nextLine();

			YoutubeSearcher youtubeSearcher = new YoutubeSearcher();
			LocalDate startDate = LocalDate.of(2024, 6, 1);
			LocalDate endDate = LocalDate.of(2024, 9, 11);
			List<Video> videos = youtubeSearcher.searchYouTubeVideos(searchQuery, startDate, endDate);

			if (videos.isEmpty()) {
				System.out.println("No videos where found.");
			} else {
				for (int i = 0; i < videos.size(); i++) {
					Video video = videos.get(i);
					System.out.printf("\n%s. %s\n %s\n", i + 1, video.getTitle(), video.getUrl());
				}
			}
		} catch (IOException e) {
			logger.error("Failed to connect to YouTube API", e);
		} catch (URISyntaxException e) {
			logger.error("Invalid URI syntax", e);
		} catch (Exception e) {
			logger.error("An unexpected error occurred", e);
		}
	}
}
