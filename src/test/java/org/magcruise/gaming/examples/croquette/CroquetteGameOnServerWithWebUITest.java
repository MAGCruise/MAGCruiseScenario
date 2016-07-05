package org.magcruise.gaming.examples.croquette;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.croquette.actor.Market;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.game.ActorName;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.sys.GameSessionOnServer;

import gnu.mapping.Symbol;

public class CroquetteGameOnServerWithWebUITest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	@Test
	public void testGameOnServerWithWebUI() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBroker(brokerUrl);
			session.setWebUI(webUI, loginId, brokerUrl);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("exp-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.build();
			session.getGameBuilder().addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					Arrays.asList(Symbol.parse("user1"), Symbol.parse("user1"),
							Symbol.parse("user1")));
			session.useAutoInput(maxAutoResponseTime);
			session.startAndWaitForFinish();

			getLatestContextAndCheckResult(session);
		}
	}

	public static void getLatestContextAndCheckResult(
			GameSessionOnServer launcher) {
		Market ctx = launcher.getLatestContext(Market.class);

		{
			Integer[] actual = new Integer[10];
			for (int i = 0; i < 10; i++) {
				Player p = ctx.getPlayer(ActorName.of("Factory"));
				Serializable v = p.getValue(Symbol.parse("profit"), i);
				actual[i] = Integer.valueOf(v.toString());

			}
			TestUtils.checkResult(CroquetteGameTest.factoryProfits, 0, 10,
					actual);
		}
		{
			Integer[] actual = new Integer[10];
			for (int i = 0; i < 10; i++) {
				Player p = ctx.getPlayer(ActorName.of("Shop1"));
				Serializable v = p.getValue(Symbol.parse("profit"), i);
				actual[i] = Integer.valueOf(v.toString());

			}
			TestUtils.checkResult(CroquetteGameTest.shop1Profits, 0, 10,
					actual);
		}
		{
			Integer[] actual = new Integer[10];
			for (int i = 0; i < 10; i++) {
				Player p = ctx.getPlayer(ActorName.of("Shop2"));
				Serializable v = p.getValue(Symbol.parse("profit"), i);
				actual[i] = Integer.valueOf(v.toString());

			}
			TestUtils.checkResult(CroquetteGameTest.shop2Profits, 0, 10,
					actual);
		}
	}
}
