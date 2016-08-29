package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.process.ProcessId;
import org.magcruise.gaming.manager.session.GameSessionOnExternalProcess;

public class CroquetteGameOnExternalProcessTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Test
	public void testRunOnExternalProcess() {

		GameSessionOnExternalProcess session = new GameSessionOnExternalProcess(
				CroquetteGameResourceLoader.class);
		session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.useAutoInput();
		session.build();
		log.info(session.toDefBootstrap());
		ProcessId pid = session.startAndWaitForFinish();
		log.debug(pid);
		CroquetteGameTest.checkResult(pid);
	}

}
