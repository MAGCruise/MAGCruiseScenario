package org.magcruise.gaming.examples.discussion;

import org.junit.Test;
import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class DiscussionGameTest {

	@Test
	public void testRun() {

		GameSession launcher = new GameSession(
				DiscussionGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.useSwingGui();
		// launcher.useAutoInput();
		launcher.startAndWaitForFinish();

	}

}
