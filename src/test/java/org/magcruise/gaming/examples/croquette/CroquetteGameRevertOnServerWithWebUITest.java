package org.magcruise.gaming.examples.croquette;

import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.lang.SExpression.ToExpressionStyle;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.boot.DefGameScript;
import org.magcruise.gaming.model.sys.GameSessionOnServer;

import gnu.mapping.Symbol;

public class CroquetteGameRevertOnServerWithWebUITest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String webUIUrl = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	private static int suspendRound = 3;

	@Test
	public void testGameOnServerWithWebUI() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameSessionOnServer launcher = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			launcher.setBroker(brokerUrl);
			launcher.setWebUI(webUIUrl, loginId, brokerUrl);
			launcher.addGameDefinitionInResource("game-definition.scm");
			launcher.addGameDefinitionInResource("exp-definition.scm");
			launcher.addDefGameScript(getRerverScript(brokerUrl));
			launcher.setLogConfiguration(Level.INFO, true);
			launcher.build();
			launcher.getGameBuilder().addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					Arrays.asList(Symbol.parse("user1"), Symbol.parse("user1"),
							Symbol.parse("user1")));
			launcher.useAutoInput(maxAutoResponseTime);
			launcher.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest
					.getLatestContextAndCheckResult(launcher);

		}
	}

	private static DefGameScript getRerverScript(String brokerUrl) {
		GameSessionOnServer launcher = new GameSessionOnServer(
				CroquetteGameResourceLoader.class);
		launcher.setBroker(brokerUrl);
		launcher.setWebUI(webUIUrl, loginId, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO);
		launcher.useAutoInput();
		launcher.setFinalRound(suspendRound);
		ProcessId pid = launcher.startAndWaitForFinish();
		log.info(pid);
		DefGameScript def = new DefGameScript(launcher.getLatestContext()
				.toSExpressionForRevert(ToExpressionStyle.MULTI_LINE));
		log.info(def);
		return def;

	}

}
