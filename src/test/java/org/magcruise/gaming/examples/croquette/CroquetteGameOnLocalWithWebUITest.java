package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.process.ProcessId;
import org.magcruise.gaming.manager.session.GameSession;

public class CroquetteGameOnLocalWithWebUITest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	@Test
	public void testGameOnLocalWithWebUI() {
		for (String brokerHost : CroquetteGameTest.brokerHosts) {
			GameSession session = new GameSession(CroquetteGameResourceLoader.class);
			session.setBrokerHost(brokerHost);
			session.useDefaultPublicWebUI(loginId);
			session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
			session.setLogConfiguration(Level.INFO);
			session.useAutoInput(maxAutoResponseTime);
			ProcessId pid = session.startAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}
}
