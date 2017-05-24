package org.magcruise.gaming.examples.ultimatum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionSeed;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.nkjmlab.util.csv.CsvUtils;
import org.nkjmlab.util.log4j.LogManager;

public class MultiUltimatumGameOnServerWithWebUIWithGData {
	protected static Logger log = LogManager.getLogger();

	public static void main(String[] args) {
		List<CroquetteGameSetting> settings = CsvUtils.readList(CroquetteGameSetting.class,
				new UltimatumGameResourceLoader().getResource("ult1.csv").toFile());
		log.info(settings);
		GameSessionsSetting gs = new GameSessionsSetting();
		gs.setSeeds(
				settings.stream().map(s -> {
					GameSessionSeed seed = new GameSessionSeed();
					seed.setSessionName(s.getGroup());
					seed.setUserIds(Arrays.asList(s.getFirstplayer(), s.getSecondplayer()));
					return seed;
				}).collect(Collectors.toList()).toArray(new GameSessionSeed[0]));
		MultiUltimatumGameOnServerWithWebUI.execGames(gs);
	}

}
