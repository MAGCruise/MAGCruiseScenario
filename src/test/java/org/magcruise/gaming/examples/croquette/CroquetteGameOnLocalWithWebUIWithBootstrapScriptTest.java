package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.boot.DefBootstrapScript;
import org.magcruise.gaming.model.sys.GameSession;
import org.magcruise.gaming.model.sys.GameSessionOnServer;

public class CroquetteGameOnLocalWithWebUIWithBootstrapScriptTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String webUIUrl = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	@Test
	public void testWebUIWithBootstrapScript() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {

			GameSession launcher = new GameSession(
					CroquetteGameResourceLoader.class);
			DefBootstrapScript b = getDefBootstrapScript(brokerUrl);
			log.info(b);
			launcher.setDefBootstrapScript(b);

			log.info(launcher.toDefBootstrap());
			ProcessId pid = launcher.startAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}

	private static DefBootstrapScript getDefBootstrapScript(String brokerUrl) {
		GameSessionOnServer launcher = new GameSessionOnServer(
				CroquetteGameResourceLoader.class);
		launcher.setBroker(brokerUrl);
		launcher.setWebUI(webUIUrl, loginId, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO);
		launcher.useAutoInput();
		return launcher.toDefBootstrap();
	}

}
