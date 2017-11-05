package org.magcruise.gaming.examples.trans_srv;

import org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class TranslationServiceGameLauncher {

	public static void main(String[] args) {
		GameSession session = new GameSession(
				TranslationServiceGameResourceLoader.class);
		session.addGameDefinitionInResource("game-definition.scm");
		session.useSwingGui();
		session.useAutoInput();
		session.start();

	}

}
