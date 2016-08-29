package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;

public class CroquetteGameOnServerWithDownloadedJarTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String jarOnWeb = "https://www.dropbox.com/s/l89k6ra1308nh4q/service-game.jar?dl=1";

	@Test
	public void testRunOnServerWithDownloadedJar() {
		for (String brokerHost : CroquetteGameTest.brokerHosts) {

			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.addClasspath(jarOnWeb);
			session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput();
			session.setBrokerHost(brokerHost);
			session.build();
			log.info(session.toDefBootstrap());
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest.getLatestContextAndCheckResult(session);
		}
	}
}
