package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.GameSessionOnServer;
import org.magcruise.gaming.model.def.boot.DefBootstrapScript;

public class CroquetteGameOnServerWithWebUIWithBootstrapScriptTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String webUIUrl = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	@Test
	public void testWebUIWithBootstrapScript() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {

			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBroker(brokerUrl);

			DefBootstrapScript bootstrapScript = getDefBootstrapScript(
					brokerUrl);
			session.setDefBootstrapScript(bootstrapScript);

			log.info(session.toDefBootstrap());
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest
					.getLatestContextAndCheckResult(session);
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
