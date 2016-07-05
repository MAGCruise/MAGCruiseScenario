package org.magcruise.gaming.examples.fish;

import org.magcruise.gaming.examples.fish.resource.FishGameResourceLoader;
import org.magcruise.gaming.manager.GameSession;

public class FishGameLauncher {

	public static void main(String[] args) {

		GameSession launcher = new GameSession(FishGameResourceLoader.class);
		launcher.addGameDefinitionInResource("fisherman.scm");
		// resourceLoader.addGameDefinitionInResource("fish.scm");
		launcher.addGameDefinitionInResource(
				"fish-game-by-interaction-protocol.scm");
		launcher.useSwingGui();
		launcher.start();

	}

}
