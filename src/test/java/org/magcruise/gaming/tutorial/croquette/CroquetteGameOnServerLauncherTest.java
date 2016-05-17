package org.magcruise.gaming.tutorial.croquette;

import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

public class CroquetteGameOnServerLauncherTest {
	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";
	private String jarOnWeb = "https://www.dropbox.com/s/sfedu5t9dca0h5p/MAGCruiseScenario.jar?dl=1";

	@Test
	public void testRunOnServer() throws InterruptedException {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useAutoInput();
		ProcessId pid = TestUtils.runOnServer(launcher);
		int millis = 10000;
		log.debug("Wait end of game {} millisec.", millis);
		Thread.sleep(millis);
		CroquetteGameLauncherTest.checkRunResult(pid);
	}

	/**
	 * http://localhost:8080/MAGCruiseBroker
	 * でBrokerを立ち上げておくこと．Dropbox上にScenario.jarを置いておくこと．
	 * サーバ上での実行と，jarダウンロード+Classpath追加を兼ねている．
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void testRunOnServerWithDownloadedJar() throws InterruptedException {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addJarOnWeb(jarOnWeb);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useAutoInput();
		ProcessId pid = TestUtils.runOnServer(launcher);
		int millis = 80000;
		log.debug("Wait end of game {} millisec.", millis);
		Thread.sleep(millis);
		CroquetteGameLauncherTest.checkRunResult(pid);
	}
}
