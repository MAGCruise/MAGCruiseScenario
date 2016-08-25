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
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBrokerUrl(brokerUrl);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("test-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput();
			log.info(session.toDefBootstrap());
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest.getLatestContextAndCheckResult(session);
		}
	}

}
