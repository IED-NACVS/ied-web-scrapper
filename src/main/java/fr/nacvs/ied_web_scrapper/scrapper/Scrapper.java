package fr.nacvs.ied_web_scrapper.scrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_web_scrapper.util.DateUtils;
import fr.nacvs.ied_web_scrapper.writer.OutputWriter;

public class Scrapper {

	private static Logger LOGGER = LoggerFactory.getLogger(Scrapper.class);

	private static final String BASE_URL = "https://www.the-numbers.com";
	private static final List<String> GENRES = List.of("Adventure", "Comedy", "Drama", "Action", "Thriller-or-Suspense", "Romantic-Comedy");
	private static final List<Integer> YEARS = IntStream.range(2000, 2016).boxed().collect(Collectors.toList());

	private OutputWriter outputWriter;
	private Connection connection;
	private String outputFolder;

	/**
	 * Create the instance and the output folder if not exists
	 */
	public Scrapper(OutputWriter outputWriter, String outputFolder) {
		super();
		this.outputWriter = outputWriter;
		this.connection = Jsoup.newSession();
		this.outputFolder = outputFolder;
		File folder = Paths.get(outputFolder).toFile();
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
	}

	public void scrapWebstite() {
		LOGGER.info("Start of scrapping");
		GENRES.forEach(this::scrapForGenre);
		LOGGER.info("End of scrapping");
	}

	private void scrapForGenre(String genre) {
		LOGGER.info("Process for genre {}", genre);
		Path filePath = Paths.get(outputFolder, outputWriter.addExtension(genre));
		File file = filePath.toFile();
		try (var writer = new BufferedWriter(new FileWriter(file))) {
			outputWriter.init(writer);
			YEARS.stream()
					.map(createUrl(genre))
					.flatMap(this::scrapFilms)
					.forEach(outputWriter::appendFilm);
			outputWriter.end();
		} catch (Exception exception) {
			LOGGER.error("Error while scrapping for the genre {} : {}", genre, exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

	private Function<Integer, String> createUrl(String genre) {
		return y -> BASE_URL + "/market/" + y + "/genre/" + genre;
	}

	private Stream<Film> scrapFilms(String url) {
		LOGGER.info("Scrap page from url {}", url);
		Document document = parseWebpage(url);
		Element body = document.body();
		// Select all rows that have at least one cell with class "data"
		return body.select("tr:has( > td.data)")
				.stream()
				.map(this::scrapOneFilm);
	}

	private Film scrapOneFilm(Element filmElement) {
		Elements tds = filmElement.getElementsByTag("td");
		Element titleElt = tds.get(1), dateElt = tds.get(2), distributorElt = tds.get(3);
		String dateStr = dateElt.text();
		String distributorStr = distributorElt.text();
		String titleStr = titleElt.text();
		// Sometimes, title is cropped in this page
		if (titleStr.endsWith("...") || titleStr.endsWith("…")) {
			titleStr = findFullFilmTitle(titleElt);
		}
		return new Film(titleStr.replace("’", "'"), DateUtils.formatFromUsDate(dateStr), distributorStr);
	}

	private String findFullFilmTitle(Element titleElt) {
		String titleStr;
		Elements linkNode = titleElt.getElementsByTag("a");
		String filmUrl = BASE_URL + linkNode.attr("href");
		LOGGER.info("Go to {} to have entire film title", filmUrl);
		Document filmDocument = parseWebpage(filmUrl);
		// Something like "Immortals (2011)"
		String titleEntireName = filmDocument.getElementsByTag("h1")
				.get(0)
				.text();
		// We want to remove the " (2011)"
		titleStr = titleEntireName.substring(0, titleEntireName.length() - 7);
		return titleStr;
	}

	private Document parseWebpage(String url) {
		Document document;
		try {
			document = connection.url(url).get();
			return document;
		} catch (IOException exception) {
			LOGGER.error("Error while executing GET on url {} : {}", url, exception.getMessage());
			throw new RuntimeException(exception);
		}
	}
}
