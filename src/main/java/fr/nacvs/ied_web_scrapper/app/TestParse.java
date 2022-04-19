package fr.nacvs.ied_web_scrapper.app;

import fr.nacvs.ied_web_scrapper.util.DateUtils;

public class TestParse {

	public static void main(String[] args) {

		String dateStr = "Mar 5, 2010";
		String date = DateUtils.formatFromUsDate(dateStr);
		System.out.println(date);
	}

}
