package org.magcruise.gaming.examples.discussion;

import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.manager.GameSession;

public class DiscussionGameLauncher {

	public static void main(String[] args) {

		GameSession launcher = new GameSession(
				DiscussionGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.useSwingGui();
		launcher.start();

	}

}
