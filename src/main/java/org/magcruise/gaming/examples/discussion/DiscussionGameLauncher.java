package org.magcruise.gaming.examples.discussion;

import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class DiscussionGameLauncher {

	public static void main(String[] args) {

		GameSession session = new GameSession(DiscussionGameResourceLoader.class);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.useSwingGui();
		session.start();

	}

}
