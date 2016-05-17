package org.magcruise.gaming.tutorial.croquette;

import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameForRevertCodeLauncher;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

import gnu.kawa.io.Path;

public class CroquetteGameRevertTest {

	@Test
	public void testRevert() {
		int suspendround = 4;
		Path revertCode = getReverCode(suspendround);

		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.addGameDefinition(revertCode);
		ProcessId pid = TestUtils.run(launcher);
		CroquetteGameLauncherTest.checkResult(pid, suspendround);
	}

	private Path getReverCode(int suspendround) {
		GameForRevertCodeLauncher revLauncher = new GameForRevertCodeLauncher(
				CroquetteGameResourceLoader.class, suspendround);
		revLauncher.addGameDefinitionInResource("game-definition.scm");
		revLauncher.addGameDefinitionInResource("def-test-players.scm");
		revLauncher.useAutoInput();
		return revLauncher.run();
	}

}
