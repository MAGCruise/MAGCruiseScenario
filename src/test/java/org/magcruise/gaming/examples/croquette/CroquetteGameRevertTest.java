package org.magcruise.gaming.examples.croquette;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameForRevertCodeLauncher;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.nkjmlab.util.db.H2Server;

import gnu.kawa.io.Path;

public class CroquetteGameRevertTest {
	@Before
	public void setUp() throws Exception {
		H2Server.start();
	}

	@Test
	public void testRevert() {
		int suspendround = 4;
		Path revertCode = getReverCode(suspendround);

		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.addGameDefinition(revertCode);
		ProcessId pid = TestUtils.run(launcher);
		CroquetteGameLauncherTest.checkResult(pid, suspendround);
	}

	private Path getReverCode(int suspendround) {
		GameForRevertCodeLauncher revLauncher = new GameForRevertCodeLauncher(
				CroquetteGameResourceLoader.class, suspendround);
		revLauncher.addGameDefinitionInResource("game-definition.scm");
		revLauncher.addGameDefinitionInResource("test-definition.scm");
		revLauncher.useAutoInput();
		return revLauncher.run();
	}

}
