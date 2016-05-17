package org.magcruise.gaming.tutorial.croquette;

import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

public class CroquetteGameOnServerLauncherTest {

	/**
	 * http://localhost:8080/MAGCruiseBroker
	 * でBrokerを立ち上げておくこと．Dropbox上にScenario.jarを置いておくこと．
	 * サーバ上での実行と，jarダウンロード+Classpath追加を兼ねている．
	 */
	@Test
	public void testRunOnServer() {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class,
				"https://www.dropbox.com/s/gzyxtkqmead2f50/MAGCruiseScenario.jar?dl=1");
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.runOnServer("http://localhost:8080/MAGCruiseBroker");
		ProcessId pid = TestUtils.exec(launcher);
		CroquetteGameLauncherTest.checkRunResult(pid);
	}
}
