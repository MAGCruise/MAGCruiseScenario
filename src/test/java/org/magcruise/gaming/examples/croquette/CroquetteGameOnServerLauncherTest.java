package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

public class CroquetteGameOnServerLauncherTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	// private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";

	private static String brokerUrl = "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker";

	@Test
	public void testRunOnServer() {
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
