package org.magcruise.gaming.examples.croquette;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.aws.GameSessionsOnServer;
import org.magcruise.gaming.examples.aws.AwsResourceLoader;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.executor.aws.AwsSettingsJson;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.nkjmlab.util.io.FileUtils;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;
import org.nkjmlab.util.time.DateTimeUtils;

import gnu.mapping.Symbol;

public class MultiCroquetteGamesOnServerWithWebUI {
	protected static Logger log = LogManager.getLogger();

	private static String loginId = "admin";
	private static int maxAutoResponseTime = 30;

	private static String awsSettingFile = "aws-settings.json";

	private static String defaultSettingFile = "settings-mid-2016.json";

	public static void main(String[] args) {
		GameSessionsSetting settings;
		if (args.length == 0) {
			settings = JsonUtils.decode(CroquetteGameResourceLoader.class
					.getResourceAsStream(defaultSettingFile), GameSessionsSetting.class);
		} else {
			log.info("Arg file is {}", args[0]);
			File file = new File(args[0]);
			settings = JsonUtils.decode(FileUtils.getFileReader(file),
					GameSessionsSetting.class);
		}

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
			session.addAssignmentRequests(Arrays.asList(Symbol.parse("Factory"),
					Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					users);
			session.setSessionName(seed.getGroup() + " ("
					+ DateTimeUtils.toTimestamp(new Date(System.currentTimeMillis())) + ")");
			session.build();
			sessions.addGameSession(session);
		});

		AwsSettingsJson awsSettings = JsonUtils.decode(AwsResourceLoader.class
				.getResourceAsStream(awsSettingFile), AwsSettingsJson.class);
		sessions.setAwsSettings(awsSettings);
		sessions.start();

	}
}
