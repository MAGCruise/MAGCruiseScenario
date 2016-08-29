package org.magcruise.gaming.examples.discussion;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class DiscussionGameWithWebUITest {

	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	private static String loginId = "admin";

	private static int maxAutoResponseTime = 1;

	@Test
	public void testWebUI() {
		GameSession launcher = new GameSession(DiscussionGameResourceLoader.class);
		launcher.useDefaultPublicWebUI(loginId);
		launcher.addGameDefinitionsInResource("game-definition.scm", "test-definition.scm");
		launcher.setLogConfiguration(Level.INFO);
		launcher.useAutoInput(maxAutoResponseTime);
		launcher.startAndWaitForFinish();
	}

}
