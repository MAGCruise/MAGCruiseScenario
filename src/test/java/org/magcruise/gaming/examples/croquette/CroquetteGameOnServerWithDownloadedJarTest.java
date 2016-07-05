package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.GameSessionOnServer;

public class CroquetteGameOnServerWithDownloadedJarTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String jarOnWeb = "https://www.dropbox.com/s/gzyxtkqmead2f50/MAGCruiseScenario.jar?dl=1";

	@Test
	public void testRunOnServerWithDownloadedJar() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {

			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBroker(brokerUrl);
			session.addClasspath(jarOnWeb);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("test-definition.scm");
			session.setLogConfiguration(Level.INFO, true);
			session.useAutoInput();
			log.info(session.toDefBootstrap());
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest
					.getLatestContextAndCheckResult(session);
		}
	}
}
