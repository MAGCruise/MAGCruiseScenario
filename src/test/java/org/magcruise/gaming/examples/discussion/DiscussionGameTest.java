package org.magcruise.gaming.examples.discussion;

import org.junit.Test;
import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class DiscussionGameTest {

	@Test
	public void testRun() {

		GameLauncher launcher = new GameLauncher(
				DiscussionGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.useSwingGui();
		// launcher.useAutoInput();
		launcher.runAndWaitForFinish();

	}

}
