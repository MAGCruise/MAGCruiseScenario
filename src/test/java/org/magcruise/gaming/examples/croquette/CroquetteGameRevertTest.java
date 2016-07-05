package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.GameSession;
import org.magcruise.gaming.manager.ProcessId;

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
		GameSession session = new GameSession(
				CroquetteGameResourceLoader.class);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.addGameDefinition(revertCode);
		session.setLogConfiguration(Level.INFO, true);
		session.useAutoInput();
		log.info(session.toDefBootstrap());
		ProcessId pid = session.startAndWaitForFinish();
		return pid;
	}

	public static Path getReverCode(int suspendround) {
		GameSession session = new GameSession(
				CroquetteGameResourceLoader.class);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setFinalRound(suspendround);
		session.setLogConfiguration(Level.INFO, true);
		session.useAutoInput();
		session.startAndWaitForFinish();
		log.info("Before revert laucher pid ={}", session.getProcessId());
		log.info("Before revert laucher={}", session);
		return session.getRevertScriptPath();
	}

}
