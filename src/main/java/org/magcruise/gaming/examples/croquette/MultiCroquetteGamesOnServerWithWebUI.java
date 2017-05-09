package org.magcruise.gaming.examples.croquette;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.aws.GameSessionsOnServer;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.executor.aws.AwsSettingsJson;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.magcruise.gaming.model.def.GameBuilder;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;

import gnu.mapping.Symbol;

public class MultiCroquetteGamesOnServerWithWebUI {
	protected static Logger log = LogManager.getLogger();

	private static String loginId = "admin";
	private static int maxAutoResponseTime = 30;

	private static String awsSettingFile = "aws-settings.json";

	public static void main(String[] args) {
		String setting = args.length == 0 ? "settings-many-2017.json" : args[0];

		GameSessionsSetting settings = JsonUtils.decode(CroquetteGameResourceLoader.class
				.getResourceAsStream(setting), GameSessionsSetting.class);

		GameSessionsOnServer sessions = new GameSessionsOnServer();

		settings.getSeeds().forEach(seed -> {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.useDefaultLocalBroker();
			session.useDefaultPublicWebUI(loginId);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("exp-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			//session.useAutoInput(maxAutoResponseTime);
			List<Symbol> users = Arrays.asList(Symbol.parse(seed.getUserIds().get(0)),
					Symbol.parse(seed.getUserIds().get(1)),
					Symbol.parse(seed.getUserIds().get(2)));
			log.info("users={}", users);
			session.addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					users);

			GameBuilder gameBuilder = session.build();
			gameBuilder.addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					users);
			sessions.addGameSession(session);
		});

		AwsSettingsJson awsSettings = JsonUtils.decode(CroquetteGameResourceLoader.class
				.getResourceAsStream(awsSettingFile), AwsSettingsJson.class);
		sessions.setAwsSettings(awsSettings);
		sessions.start();

	}
}
