package fr.nacvs.ied_web_scrapper.writer;

import java.io.Writer;

import fr.nacvs.ied_web_scrapper.scrapper.Film;

public interface OutputWriter {
	
	String addExtension(String baseFilename);

	/**
	 * Init the instance and / or the file. For example, the implementation can
	 * write some premliminal data to the file.
	 */
	void init(Writer writer);
	
	void appendFilm(Film film);
	
	/**
	 * Some implementations can need to do something before closing the file.
	 */
	void end();
}
