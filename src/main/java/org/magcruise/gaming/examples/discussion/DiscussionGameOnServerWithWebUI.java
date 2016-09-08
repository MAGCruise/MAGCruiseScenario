package org.magcruise.gaming.examples.discussion;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.discussion.resource.DiscussionGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionOnServer;

public class DiscussionGameOnServerWithWebUI {

	private static String loginId = "wb15718";

	public static void main(String[] args) {
		GameSessionOnServer session = new GameSessionOnServer(DiscussionGameResourceLoader.class);
		session.useDefaultPublicWebUI(loginId);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.startAndWaitForFinish();
	}

}
