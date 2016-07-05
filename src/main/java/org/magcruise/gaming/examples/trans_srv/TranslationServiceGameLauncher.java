package org.magcruise.gaming.examples.trans_srv;

import org.magcruise.gaming.examples.trans_srv.resource.TranslationServiceGameResourceLoader;
import org.magcruise.gaming.model.sys.GameSession;

public class TranslationServiceGameLauncher {

	public static void main(String[] args) {
		GameSession session = new GameSession(
				TranslationServiceGameResourceLoader.class);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("connect-to-webui-sample.scm");
		// launcher.useSwingGui();
		// launcher.useAutoInput();
		session.start();

	}

}
