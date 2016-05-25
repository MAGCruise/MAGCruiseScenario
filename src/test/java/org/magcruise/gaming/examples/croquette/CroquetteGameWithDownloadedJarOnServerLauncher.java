package org.magcruise.gaming.examples.croquette;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameWithDownloadedJarOnServerLauncher {
	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";
	private String jarOnWeb = "https://www.dropbox.com/s/sfedu5t9dca0h5p/MAGCruiseScenario.jar?dl=1";

	@Before
	public void setUp() throws Exception {
		H2Server.start();
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
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.useAutoInput();
		ProcessId pid = TestUtils.run(launcher);
		int millis = 80000;
		log.debug("Wait end of game {} millisec.", millis);
		Thread.sleep(millis);
		CroquetteGameLauncherTest.checkResult(pid);
	}
}
