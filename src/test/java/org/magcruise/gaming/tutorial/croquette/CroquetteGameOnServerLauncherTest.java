package org.magcruise.gaming.tutorial.croquette;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameOnServerLauncherTest {
	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";

	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRunOnServer() throws InterruptedException {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useAutoInput();
		ProcessId pid = TestUtils.run(launcher);
		int millis = 10000;
		log.debug("Wait end of game {} millisec.", millis);
		Thread.sleep(millis);
		CroquetteGameLauncherTest.checkResult(pid);
	}

}
