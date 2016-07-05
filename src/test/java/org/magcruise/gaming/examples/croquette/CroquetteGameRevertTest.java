package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameSession;

import gnu.kawa.io.Path;

public class CroquetteGameRevertTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Test
	public void testRevert() {
		int suspendround = 4;
		Path revertCode = getReverCode(suspendround);
		ProcessId pid = restart(revertCode, suspendround);
		CroquetteGameTest.checkResult(pid, suspendround);

	}

	private ProcessId restart(Path revertCode, int suspendround) {
		GameSession launcher = new GameSession(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.addGameDefinition(revertCode);
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		log.info(launcher.toDefBootstrap());
		ProcessId pid = launcher.startAndWaitForFinish();
		return pid;
	}

	public static Path getReverCode(int suspendround) {
		GameSession launcher = new GameSession(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setFinalRound(suspendround);
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		launcher.startAndWaitForFinish();
		log.info("Before revert laucher pid ={}", launcher.getProcessId());
		log.info("Before revert laucher={}", launcher);
		return launcher.getRevertScriptPath();
	}

}
