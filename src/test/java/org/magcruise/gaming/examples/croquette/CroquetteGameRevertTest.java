package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameForRevertCodeLauncher;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.nkjmlab.util.db.H2Server;

import gnu.kawa.io.Path;

public class CroquetteGameRevertTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRevert() {
		int suspendround = 4;
		Path revertCode = getReverCode(suspendround);
		ProcessId pid = restart(revertCode, suspendround);
		CroquetteGameLauncherTest.checkResult(pid, suspendround);

	}

	private ProcessId restart(Path revertCode, int suspendround) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.addGameDefinition(revertCode);
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		log.info(launcher.toDefBootstrap());
		ProcessId pid = launcher.runAndWaitForFinish();
		return pid;
	}

	private Path getReverCode(int suspendround) {
		GameForRevertCodeLauncher launcher = new GameForRevertCodeLauncher(
				CroquetteGameResourceLoader.class, suspendround);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		launcher.runAndWaitForFinish();
		log.info("Before revert laucher pid ={}", launcher.getProcessId());
		log.info("Before revert laucher={}", launcher);
		return launcher.getPathOfRevertCode();
	}

}
