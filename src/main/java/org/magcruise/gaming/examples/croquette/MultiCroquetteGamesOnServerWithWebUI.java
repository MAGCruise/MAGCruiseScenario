package org.magcruise.gaming.examples.croquette;

import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.aws.GameSessionsOnServer;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.executor.aws.AwsSettingsJson;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.magcruise.gaming.model.def.GameBuilder;
import org.nkjmlab.util.json.JsonUtils;

import gnu.mapping.Symbol;

public class MultiCroquetteGamesOnServerWithWebUI {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String loginId = "reiko";
	private static int maxAutoResponseTime = 10;

	public static void main(String[] args) {

		GameSessionsSetting settings = JsonUtils.decode(CroquetteGameResourceLoader.class
				.getResourceAsStream("settings-mid.json"), GameSessionsSetting.class);

		GameSessionsOnServer sessions = new GameSessionsOnServer();
		
		settings.getSeeds().forEach(seed -> {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.useDefaultLocalBroker();
			session.useDefaultPublicWebUI(loginId);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("exp-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput(maxAutoResponseTime);
			GameBuilder gameBuilder = session.build();
			gameBuilder.addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					Arrays.asList(Symbol.parse(seed.getUserIds().get(0)),
							Symbol.parse(seed.getUserIds().get(1)),
							Symbol.parse(seed.getUserIds().get(2))));
			sessions.addGameSession(session);
		});

		AwsSettingsJson awsSettings = JsonUtils.decode(CroquetteGameResourceLoader.class
				.getResourceAsStream("aws-settings.json"), AwsSettingsJson.class);
		sessions.setAwsSettings(awsSettings);
		sessions.start();

	}
}
