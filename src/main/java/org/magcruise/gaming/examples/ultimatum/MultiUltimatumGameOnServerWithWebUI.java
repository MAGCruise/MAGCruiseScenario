package org.magcruise.gaming.examples.ultimatum;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.aws.GameSessionsOnServer;
import org.magcruise.gaming.examples.aws.AwsResourceLoader;
import org.magcruise.gaming.examples.ultimatum.actor.FirstPlayer;
import org.magcruise.gaming.examples.ultimatum.actor.SecondPlayer;
import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.executor.aws.AwsSettingsJson;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.magcruise.gaming.model.def.actor.DefPlayer;
import org.magcruise.gaming.model.game.Player.PlayerType;
import org.nkjmlab.util.io.FileUtils;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;

import gnu.mapping.Symbol;

public class MultiUltimatumGameOnServerWithWebUI {
	protected static Logger log = LogManager.getLogger();

	private static String loginId = "admin";
	private static int maxAutoResponseTime = 30;

	private static String awsSettingFile = "aws-settings.json";

	//private static String defaultSettingFile = "settings-all-anonymous-2017.json";
	private static String defaultSettingFile = "settings-all-anonymous-2017-2nd.json";

	public static void main(String[] args) {
		GameSessionsSetting settings;
		if (args.length == 0) {
			settings = JsonUtils.decode(UltimatumGameResourceLoader.class
					.getResourceAsStream(defaultSettingFile), GameSessionsSetting.class);
		} else {
			log.info("Arg file is {}", args[0]);
			File file = new File(args[0]);
			settings = JsonUtils.decode(FileUtils.getFileReader(file),
					GameSessionsSetting.class);
		}
		execGames(settings);

	}

	public static void execGames(GameSessionsSetting settings) {

		GameSessionsOnServer sessions = new GameSessionsOnServer();

		settings.getSeeds().forEach(seed -> {
			GameSessionOnServer session = new GameSessionOnServer(
					UltimatumGameResourceLoader.class);
			session.useDefaultLocalBroker();
			session.useDefaultPublicWebUI(loginId);
			session.addGameDefinitionInResource("game-definition.scm");

			session.setLogConfiguration(Level.INFO, true);
			//session.useAutoInput(maxAutoResponseTime);

			String firstPlayerUserId = seed.getUserIds().get(0);
			String secondPlayerUserId = seed.getUserIds().get(1);

			session.addDefPlayer(new DefPlayer(FirstPlayer.FIRST_PLAYER,
					getPlayerType(firstPlayerUserId), FirstPlayer.class));

			session.addDefPlayer(new DefPlayer(SecondPlayer.SECOND_PLAYER,
					getPlayerType(secondPlayerUserId), SecondPlayer.class));

			List<Symbol> users = Arrays.asList(Symbol.parse(removeAgentString(firstPlayerUserId)),
					Symbol.parse(removeAgentString(secondPlayerUserId)));

			log.info("users={}", users);
			session.addAssignmentRequests(
					Arrays.asList(FirstPlayer.FIRST_PLAYER.toSymbol(),
							SecondPlayer.SECOND_PLAYER.toSymbol()),
					users);
			session.setSessionName(seed.getSessionName());

			session.build();
			sessions.addGameSession(session);
		});

		AwsSettingsJson awsSettings = JsonUtils.decode(AwsResourceLoader.class
				.getResourceAsStream(awsSettingFile),
				AwsSettingsJson.class);
		sessions.setAwsSettings(awsSettings);
		sessions.start();
	}

	private static PlayerType getPlayerType(String userId) {
		return userId.startsWith("AGENT") ? PlayerType.AGENT : PlayerType.HUMAN;
	}

	private static String removeAgentString(String userId) {
		return userId.startsWith("AGENT") ? userId.replaceFirst("AGENT", "") : userId;
	}
}
