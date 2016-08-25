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
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameSession session = new GameSession(
					CroquetteGameResourceLoader.class);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("test-definition.scm");
			session.setBrokerUrl(brokerUrl);
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput();
			log.info(session.toDefBootstrap());
			ProcessId pid = session.startAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}
}
