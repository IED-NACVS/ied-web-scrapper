package fr.nacvs.ied_web_scrapper.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtils {

	/**
	 * Formatter for dates like Jun 29, 2011
	 */
	public static final DateTimeFormatter FORMATTER_US = DateTimeFormatter.ofPattern("MMM d, yyyy")
			.withLocale(Locale.US);

	public static final DateTimeFormatter FORMATTER_OUTPUT = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	/**
	 * Format a date of format like "Jun 29, 2011" to a format like "6/29/2011"
	 */
	public static String formatFromUsDate(String usDate) {
		return  LocalDate.parse(usDate, FORMATTER_US).format(FORMATTER_OUTPUT);
	}
}
