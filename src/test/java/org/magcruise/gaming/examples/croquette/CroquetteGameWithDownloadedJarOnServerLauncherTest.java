package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;
import org.nkjmlab.util.db.H2Server;

public class CroquetteGameWithDownloadedJarOnServerLauncherTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private String brokerUrl = "http://localhost:8080/MAGCruiseBroker";
	private String jarOnWeb = "http://www.dropbox.com/s/gzyxtkqmead2f50/MAGCruiseScenario.jar?dl=1";

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
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		ProcessId pid = launcher.runAndWaitForFinish();
		CroquetteGameLauncherTest.checkResult(pid);
	}
}
