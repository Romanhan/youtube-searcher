package eu.romanhan.youtube;

public class Video {

	private final String url;
	private final String title;

	public Video(String url, String description) {
		this.url = url;
		this.title = description;
	}

	public String getUrl() {
		return url;
	}

	public String getTitle() {
		return title;
	}

}
