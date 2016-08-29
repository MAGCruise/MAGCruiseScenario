package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.process.ProcessId;
import org.magcruise.gaming.manager.session.GameSession;

public class CroquetteGameOnLocalWithBrokerTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Test
	public void testRunWithServer() {
		for (String brokerHost : CroquetteGameTest.brokerHosts) {
			GameSession session = new GameSession(CroquetteGameResourceLoader.class);
			session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
			session.setBrokerHost(brokerHost);
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput();
			session.build();
			log.info(session.toDefBootstrap());
			ProcessId pid = session.startAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}
}
