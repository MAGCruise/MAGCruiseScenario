package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnLocalWithBrokerLauncher;

public class CroquetteGameOnLocalWithBrokerTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Test
	public void testRunWithServer() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameOnLocalWithBrokerLauncher launcher = new GameOnLocalWithBrokerLauncher(
					CroquetteGameResourceLoader.class, brokerUrl);
			launcher.addGameDefinitionInResource("game-definition.scm");
			launcher.addGameDefinitionInResource("test-definition.scm");
			launcher.setLogConfiguration(Level.INFO, true);
			launcher.useAutoInput();
			log.info(launcher.toDefBootstrap());
			ProcessId pid = launcher.runAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}
}
