package org.magcruise.gaming.examples.croquette;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnExternalProcessLauncher;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameOnExternalProcessTest {
	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRunOnExternalProcess() throws InterruptedException {

		GameOnExternalProcessLauncher launcher = new GameOnExternalProcessLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		ProcessId pid = TestUtils.run(launcher);
		log.debug(pid);
		Thread.sleep(10000);
		CroquetteGameLauncherTest.checkResult(pid);
	}

}
