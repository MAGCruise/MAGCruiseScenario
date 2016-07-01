package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

public class CroquetteGameOnServerWithDownloadedJarTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker";

	// private static String brokerUrl =
	// "http://localhost:8080/MAGCruiseBroker";
	private static String jarOnWeb = "https://www.dropbox.com/s/gzyxtkqmead2f50/MAGCruiseScenario.jar?dl=1";

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
		launcher.addScenarioJarOnWeb(jarOnWeb);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		log.info(launcher.toDefBootstrap());
		launcher.runAndWaitForFinish();
		CroquetteGameOnServerWithWebUITest
				.getLatestContextAndCheckResult(launcher);

	}
}
