package fr.nacvs.ied_web_scrapper.app;

import fr.nacvs.ied_web_scrapper.scrapper.Scrapper;
import fr.nacvs.ied_web_scrapper.writer.CsvWriter;
import fr.nacvs.ied_web_scrapper.writer.OutputWriter;

public class ScrapperApp {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("The argument \"outputFolder\" isMissing");
			System.err.println("Usage : java -jar ScrapperApp.jar /path/to/folder");
		}
		
		OutputWriter outputWriter = new CsvWriter();
		Scrapper scrapper = new Scrapper(outputWriter, args[0]);
		scrapper.scrapWebstite();
	}

}
