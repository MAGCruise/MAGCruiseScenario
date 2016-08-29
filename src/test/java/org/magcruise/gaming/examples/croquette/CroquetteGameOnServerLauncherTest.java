package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;

public class CroquetteGameOnServerLauncherTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Test
	public void testRunOnServer() {
		for (String brokerHost : CroquetteGameTest.brokerHosts) {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBrokerHost(brokerHost);
			session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput();
			session.build();
			log.info(session.toDefBootstrap());
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest.getLatestContextAndCheckResult(session);
		}
	}

}
