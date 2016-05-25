package org.magcruise.gaming.examples.discussion;

import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

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
