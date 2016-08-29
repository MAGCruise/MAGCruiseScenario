package org.magcruise.gaming.examples.croquette;

import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.lang.SExpression.ToExpressionStyle;
import org.magcruise.gaming.manager.process.ProcessId;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.model.def.boot.DefGameScript;

import gnu.mapping.Symbol;

public class CroquetteGameRevertOnServerWithWebUITest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	private static int suspendRound = 3;

	@Test
	public void testGameOnServerWithWebUI() {
		for (String brokerHost : CroquetteGameTest.brokerHosts) {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBrokerHost(brokerHost);
			session.useDefaultPublicWebUI(loginId);
			session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
			session.addDefGameScript(getRerverScript(brokerHost));
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput(maxAutoResponseTime);
			session.build();
			session.getGameBuilder().addAssignmentRequests(
					Arrays.asList(Symbol.parse("Factory"),
							Symbol.parse("Shop1"), Symbol.parse("Shop2")),
					Arrays.asList(Symbol.parse("user1"), Symbol.parse("user1"),
							Symbol.parse("user1")));
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest.getLatestContextAndCheckResult(session);

		}
	}

	private static DefGameScript getRerverScript(String brokerHost) {
		GameSessionOnServer session = new GameSessionOnServer(CroquetteGameResourceLoader.class);
		session.setBrokerHost(brokerHost);
		session.useDefaultPublicWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO);
		session.useAutoInput();
		session.setFinalRound(suspendRound);
		ProcessId pid = session.startAndWaitForFinish();
		log.info(pid);
		DefGameScript def = new DefGameScript(session.getLatestContext()
				.toSExpressionForRevert(ToExpressionStyle.MULTI_LINE));
		log.info(def);
		return def;

	}

}
