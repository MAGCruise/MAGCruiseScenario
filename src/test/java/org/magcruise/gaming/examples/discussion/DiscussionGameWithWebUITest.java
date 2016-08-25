package org.magcruise.gaming.examples.discussion;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class DiscussionGameWithWebUITest {

	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String brokerUrl = "http://proxy.robin.toho.magcruise.org/magcruise-broker";
	// private static String brokerUrl =
	// "http://proxy.phoenix.toho.magcruise.org/magcruise-broker";
	// private static String brokerUrl =
	// "http://waseda1.magcruise.org/magcruise-broker";
	private static String webUIUrl = "http://toho.magcruise.org/world/BackendAPIService";
	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	@Test
	public void testWebUI() {
		GameSession launcher = new GameSession(
				DiscussionGameResourceLoader.class);
		launcher.setBrokerUrl(brokerUrl);
		launcher.setWebUI(webUIUrl, loginId, brokerUrl);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO);
		launcher.useAutoInput(maxAutoResponseTime);
		launcher.startAndWaitForFinish();
	}

}
