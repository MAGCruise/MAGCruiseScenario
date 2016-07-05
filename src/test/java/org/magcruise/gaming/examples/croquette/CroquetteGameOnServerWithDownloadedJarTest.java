package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameSessionOnServer;

public class CroquetteGameOnServerWithDownloadedJarTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String jarOnWeb = "https://www.dropbox.com/s/gzyxtkqmead2f50/MAGCruiseScenario.jar?dl=1";

	@Test
	public void testRunOnServerWithDownloadedJar() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {

			GameSessionOnServer launcher = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			launcher.setBroker(brokerUrl);
			launcher.addClasspath(jarOnWeb);
			launcher.addGameDefinitionInResource("game-definition.scm");
			launcher.addGameDefinitionInResource("test-definition.scm");
			launcher.setLogConfiguration(Level.INFO, true);
			launcher.useAutoInput();
			log.info(launcher.toDefBootstrap());
			launcher.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest
					.getLatestContextAndCheckResult(launcher);
		}
	}
}
