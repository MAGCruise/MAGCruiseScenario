package org.magcruise.gaming.examples.ultimatum;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.aws.GameSessionsOnServer;
import org.magcruise.gaming.examples.aws.AwsResourceLoader;
import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.executor.aws.AwsSettingsJson;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.nkjmlab.util.io.FileUtils;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;
import org.nkjmlab.util.time.DateTimeUtils;

import gnu.mapping.Symbol;

public class MultiUltimatumGameOnServerWithWebUI {
	protected static Logger log = LogManager.getLogger();

	private static String loginId = "admin";

	private static String awsSettingFile = "aws-settings.json";

	public static void main(String[] args) {
		GameSessionsSetting settings;
		if (args.length == 0) {
			String setting = "settings-mid-2016.json";
			settings = JsonUtils.decode(UltimatumGameResourceLoader.class
					.getResourceAsStream(setting), GameSessionsSetting.class);
		} else {
			log.info("Arg file is {}", args[0]);
			File file = new File(args[0]);
			settings = JsonUtils.decode(FileUtils.getFileReader(file),
					GameSessionsSetting.class);
		}

		GameSessionsOnServer sessions = new GameSessionsOnServer();

		settings.getSeeds().forEach(seed -> {
			GameSessionOnServer session = new GameSessionOnServer(
					UltimatumGameResourceLoader.class);
			session.useDefaultLocalBroker();
			session.useDefaultPublicWebUI(loginId);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("exp-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			List<Symbol> users = Arrays.asList(Symbol.parse(seed.getUserIds().get(0)),
					Symbol.parse(seed.getUserIds().get(1)));
			log.info("users={}", users);
			session.addAssignmentRequests(
					Arrays.asList(Symbol.parse("BigBear"), Symbol.parse("SmallBear")), users);
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
