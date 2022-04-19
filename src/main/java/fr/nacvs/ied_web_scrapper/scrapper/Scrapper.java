package fr.nacvs.ied_web_scrapper.scrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.nacvs.ied_web_scrapper.writer.OutputWriter;

public class Scrapper {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Scrapper.class);

	private static final String BASE_URL = "https://www.the-numbers.com/market/";
	private static final List<String> GENRES = List.of("Adventure", "Comedy", "Drama", "Action", "Thriller-or-Suspense", "Romantic-Comedy");
	private static final List<Integer> YEARS = IntStream.range(2010, 2016).boxed().collect(Collectors.toList());
	
	private OutputWriter outputWriter;
	private String outputFolder;
	
	/**
	 * Create the instance and the output folder if not exists 
	 */
	public Scrapper(OutputWriter outputWriter, String outputFolder) {
		super();
		this.outputWriter = outputWriter;
		this.outputFolder = outputFolder;
		File folder = Paths.get(outputFolder).toFile();
		if (!folder.isDirectory()) {
			folder.mkdirs();
		}
	}

	public void scrapWebstite() {
		GENRES.forEach(this::scrapForGenre);
	}
	
	private void scrapForGenre(String genre) {
		Path filePath = Paths.get(outputFolder, outputWriter.addExtension(genre));
		File file = filePath.toFile();
		try (var writer = new BufferedWriter(new FileWriter(file))){
			outputWriter.init(writer);
			YEARS.stream()
				.map(createUrl(genre))
				.map(this::scrapFilms)
				.flatMap(List::stream)
				.forEach(outputWriter::appendFilm);
			outputWriter.end();
		} catch (Exception exception) {
			LOGGER.error("Error while scrapping for the genre {} : {}", genre, exception.getMessage());
			throw new RuntimeException(exception);
		}
	}
	
	private Function<Integer, String> createUrl(String genre) {
		return y -> BASE_URL + genre + "/" + y;
	}
	
	private List<Film> scrapFilms(String url) {
		
	}
}
