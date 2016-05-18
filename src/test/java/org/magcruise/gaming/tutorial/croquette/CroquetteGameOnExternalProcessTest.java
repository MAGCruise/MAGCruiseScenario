package org.magcruise.gaming.tutorial.croquette;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnExternalProcessLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameOnExternalProcessTest {
	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRunOnExternalProcess() throws InterruptedException {

		GameOnExternalProcessLauncher launcher = new GameOnExternalProcessLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		ProcessId pid = TestUtils.run(launcher);
		Thread.sleep(10000);
		CroquetteGameLauncherTest.checkResult(pid);
	}

}
