package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

public class CroquetteGameOnServerLauncherTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Test
	public void testRunOnServer() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameOnServerLauncher launcher = new GameOnServerLauncher(
					CroquetteGameResourceLoader.class, brokerUrl);
			launcher.addGameDefinitionInResource("game-definition.scm");
			launcher.addGameDefinitionInResource("test-definition.scm");
			launcher.setLogConfiguration(Level.INFO, true);
			launcher.useAutoInput();
			log.info(launcher.toDefBootstrap());
			launcher.runAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest
					.getLatestContextAndCheckResult(launcher);
		}
	}

}
