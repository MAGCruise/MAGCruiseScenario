package org.magcruise.gaming.examples.ultimatum;

import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class UltimatumGameLauncher {

	public static void main(String[] args) {
		GameSession session = new GameSession(UltimatumGameResourceLoader.class);
		// launcher.setBootstrapInResource("bootstrap.scm");
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("exp-definition.scm");
		session.useSwingGui();
		// launcher.useAutoInput();
		session.start();

	}

}
