package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.GameSession;
import org.magcruise.gaming.manager.ProcessId;

public class CroquetteGameOnLocalWithWebUITest {
	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String webUI = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	@Test
	public void testGameOnLocalWithWebUI() {
		for (String brokerUrl : CroquetteGameTest.brokerUrls) {
			GameSession session = new GameSession(
					CroquetteGameResourceLoader.class);
			session.setBroker(brokerUrl);
			session.setWebUI(webUI, loginId, brokerUrl);
			session.addGameDefinitionInResource("game-definition.scm");
			session.addGameDefinitionInResource("test-definition.scm");
			session.setLogConfiguration(Level.INFO);
			session.useAutoInput(maxAutoResponseTime);
			ProcessId pid = session.startAndWaitForFinish();
			CroquetteGameTest.checkResult(pid);
		}
	}
}
