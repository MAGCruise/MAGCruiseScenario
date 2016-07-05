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

			GameSession session = new GameSession(
					CroquetteGameResourceLoader.class);
			DefBootstrapScript b = getDefBootstrapScript(brokerUrl);
			log.info(b);
			session.setDefBootstrapScript(b);

			log.info(session.toDefBootstrap());
			ProcessId pid = session.startAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}

	private static DefBootstrapScript getDefBootstrapScript(String brokerUrl) {
		GameSessionOnServer session = new GameSessionOnServer(
				CroquetteGameResourceLoader.class);
		session.setBroker(brokerUrl);
		session.setWebUI(webUIUrl, loginId, brokerUrl);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO);
		session.useAutoInput();
		return session.toDefBootstrap();
	}

}
