package fr.nacvs.ied_web_scrapper.scrapper;

/**
 * The data for one film
 */
public class Film {

	private String title;
	private String date;
	private String distributor;

	public Film(String title, String date, String distributor) {
		super();
		this.title = title;
		this.date = date;
		this.distributor = distributor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
}
