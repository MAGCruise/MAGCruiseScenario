package org.magcruise.gaming.examples.croquette;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.aws.GameSessionsOnAwsServer;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;

import gnu.mapping.Symbol;

public class MultiCroquetteGamesOnAwsServerWithWebUI {
	protected static Logger log = LogManager.getLogger();

	private static int maxAutoResponseTime = 30;

	private static String defaultSettingFile = "croquette-settings-many-2018.json";

	public static void main(String[] args) {
		try {
			GameSessionsSetting settings;
			if (args.length == 0 || args[0].length() == 0) {
				settings = JsonUtils.decode(CroquetteGameResourceLoader.class
						.getResourceAsStream(defaultSettingFile), GameSessionsSetting.class);
			} else {
				log.info("Arg is {}", args[0]);
				settings = JsonUtils.decode(args[0], GameSessionsSetting.class);
			}
			log.info(settings);

			execGame(settings);
		} catch (Exception e) {
			log.error(e, e);
		}

	}

	private static void execGame(GameSessionsSetting settings) {
		GameSessionsOnAwsServer sessions = new GameSessionsOnAwsServer();
		sessions.setAwsSettings(settings.getAwsServersSetting());

		settings.getSeeds().forEach(seed -> {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.useDefaultLocalBroker();
			session.useDefaultPublicWebUI(settings.getOwnerId());
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("exp-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			//session.useAutoInput(maxAutoResponseTime);
			List<Symbol> users = Arrays.asList(Symbol.parse(seed.getUserIds().get(0)),
					Symbol.parse(seed.getUserIds().get(1)),
					Symbol.parse(seed.getUserIds().get(2)));
			log.info("users={}", users);
			session.addAssignmentRequests(Arrays.asList(Symbol.parse("Factory"),
					Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					users);
			session.setSessionName(seed.getSessionName());
			session.build();
			sessions.addGameSession(session);
		});

		log.info("Sessions are created. {}", sessions);
		sessions.start();
	}
}
