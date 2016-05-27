package org.magcruise.gaming.examples.croquette;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnLocalWithSeverLauncher;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameOnLocalWithServer {
	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	// private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";

	private String brokerUrl = "http://phoenix.toho.magcruise.org/MAGCruiseBroker";

	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRunWithServer() throws InterruptedException {
		GameOnLocalWithSeverLauncher launcher = new GameOnLocalWithSeverLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		ProcessId pid = TestUtils.run(launcher);
		int millis = 10000;
		log.debug("Wait end of game {} millisec.", millis);
		Thread.sleep(millis);
		CroquetteGameLauncherTest.checkResult(pid);
	}
}
