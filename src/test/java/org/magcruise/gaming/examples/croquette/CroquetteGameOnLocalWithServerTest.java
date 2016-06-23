package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnLocalWithSeverLauncher;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameOnLocalWithServerTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";

	// private String brokerUrl =
	// "http://phoenix.toho.magcruise.org/MAGCruiseBroker";

	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRunWithServer() {
		GameOnLocalWithSeverLauncher launcher = new GameOnLocalWithSeverLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		ProcessId pid = launcher.runAndWaitForFinish();
		CroquetteGameLauncherTest.checkResult(pid);
	}
}
