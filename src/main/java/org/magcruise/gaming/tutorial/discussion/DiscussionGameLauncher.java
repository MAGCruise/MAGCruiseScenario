package org.magcruise.gaming.tutorial.discussion;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.discussion.resource.DiscussionGameResourceLoader;

public class DiscussionGameLauncher {

	public static void main(String[] args) {

		GameLauncher launcher = new GameLauncher(
				DiscussionGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.useSwingGui();
		launcher.run();

	}

}
