package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;
import org.magcruise.gaming.model.def.boot.DefBootstrapScript;

public class CroquetteGameOnServerWithWebUIWithBootstrapScriptTest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String loginId = "admin";

	@Test
	public void testWebUIWithBootstrapScript() {
		for (String brokerHost : CroquetteGameTest.brokerHosts) {

			GameSessionOnServer session = new GameSessionOnServer(
					CroquetteGameResourceLoader.class);
			session.setBrokerHost(brokerHost);

			DefBootstrapScript bootstrapScript = getDefBootstrapScript(brokerHost);
			session.setDefBootstrapScript(bootstrapScript);
			session.build();
			log.info(session.toDefBootstrap());
			session.startAndWaitForFinish();
			CroquetteGameOnServerWithWebUITest.getLatestContextAndCheckResult(session);
		}
	}

	private static DefBootstrapScript getDefBootstrapScript(String brokerHost) {
		GameSessionOnServer session = new GameSessionOnServer(
				CroquetteGameResourceLoader.class);
		session.setBrokerHost(brokerHost);
		session.useDefaultPublicWebUI(loginId);
		session.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
		session.setLogConfiguration(Level.INFO);
		session.useAutoInput();
		session.build();
		return session.toDefBootstrap();
	}

}
