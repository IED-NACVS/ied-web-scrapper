package fr.nacvs.ied_web_scrapper.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import fr.nacvs.ied_web_scrapper.scrapper.Film;

public class CsvWriter implements OutputWriter {
	
	public static final String SEPARATOR = "\t";

	private final List<String> headers = List.of("Title", "Date", "Distributor");
	private Writer writer;
	
	@Override
	public String addExtension(String baseFilename) {
		return baseFilename + ".csv";
	}

	@Override
	public void init(Writer writer) {
		this.writer = writer;
		String headerLine = String.join(SEPARATOR, headers) + System.lineSeparator();
		writeLine(headerLine);
	}

	@Override
	public void appendFilm(Film film) {
		String line = new StringBuilder()
			.append(film.getTitle())
			.append(SEPARATOR)
			.append(film.getDate())
			.append(SEPARATOR)
			.append(film.getDistributor())
			.append(System.lineSeparator())
			.toString();
		writeLine(line);
	}

	@Override
	public void end() {
		// Nothing to do for a csv file
	}
	
	private void writeLine(String line) {
		try {
			this.writer.write(line);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}
