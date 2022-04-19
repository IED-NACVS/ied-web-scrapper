package fr.nacvs.ied_web_scrapper.app;

import fr.nacvs.ied_web_scrapper.scrapper.Scrapper;

public class ScrapperApp {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("The argument \"outputFolder\" isMissing");
			System.err.println("Usage : java -jar ScrapperApp.jar /path/to/folder");
		}
		
		Scrapper scrapper = new Scrapper(args[0]);
		scrapper.scrapWebstite();
	}

}
