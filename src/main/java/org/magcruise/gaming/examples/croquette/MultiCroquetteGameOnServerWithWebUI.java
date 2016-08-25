package org.magcruise.gaming.examples.croquette;

import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.MultiGameSessionSettings;

import gnu.mapping.Symbol;
import jp.go.nict.langrid.repackaged.net.arnx.jsonic.JSONException;
import net.arnx.jsonic.JSON;

public class MultiCroquetteGameOnServerWithWebUI {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://toho.magcruise.org//magcruise-broker";
	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "reiko";
	private static int maxAutoResponseTime = 10;

	public static void main(String[] args) throws JSONException, IOException {

		MultiGameSessionSettings settings = JSON.decode(CroquetteGameResourceLoader.class
				.getResourceAsStream("settings.json"), MultiGameSessionSettings.class);

		settings.getSeeds().forEach(seed -> {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBrokerUrl(brokerUrl);
			session.setWebUI(webUI, loginId, brokerUrl);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("exp-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.build();
			session.getGameBuilder().addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					Arrays.asList(Symbol.parse(seed.getUserIds().get(0)),
							Symbol.parse(seed.getUserIds().get(1)),
							Symbol.parse(seed.getUserIds().get(2))));
			session.useAutoInput(maxAutoResponseTime);
			session.start();
		});

	}
}
