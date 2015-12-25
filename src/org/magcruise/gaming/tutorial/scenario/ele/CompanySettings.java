package org.magcruise.gaming.tutorial.scenario.ele;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.magcruise.gaming.model.game.Context;

import com.orangesignal.csv.CsvConfig;
import com.orangesignal.csv.manager.CsvEntityManager;

public class CompanySettings {

	public static List<CompanySetting> readSettings(Context ctx) {
		List<CompanySetting> companies = null;
		try {
			CsvConfig cfg = new CsvConfig();
			cfg.setQuoteDisabled(false);
			cfg.setIgnoreEmptyLines(true);
			cfg.setIgnoreLeadingWhitespaces(true);
			cfg.setIgnoreTrailingWhitespaces(true);
			CsvEntityManager manager = new CsvEntityManager(cfg);

			companies = manager.load(CompanySetting.class)
					.from(new File("scenario/ele/companies.csv"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return companies;
	}

}
