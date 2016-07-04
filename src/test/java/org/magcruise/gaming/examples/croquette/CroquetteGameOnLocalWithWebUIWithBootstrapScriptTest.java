package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.boot.DefBootstrapScript;
import org.magcruise.gaming.model.def.sys.DefUIServiceAndRegisterSession;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

public class CroquetteGameOnLocalWithWebUIWithBootstrapScriptTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	@Test
	public void testWebUIWithBootstrapScript() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {

			GameLauncher launcher = new GameLauncher(
					CroquetteGameResourceLoader.class);
			DefBootstrapScript b = getDefBootstrapScript(brokerUrl);
			log.info(b);
			launcher.setDefBootstrapScript(b);

			log.info(launcher.toDefBootstrap());
			ProcessId pid = launcher.runAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}

	private static DefBootstrapScript getDefBootstrapScript(String brokerUrl) {
		GameOnServerLauncher launcher = new GameOnServerLauncher(
				CroquetteGameResourceLoader.class, brokerUrl);
		launcher.addDefUI(
				new DefUIServiceAndRegisterSession(webUI, loginId, brokerUrl));
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO);
		launcher.useAutoInput();
		return launcher.toDefBootstrap();
	}

}
