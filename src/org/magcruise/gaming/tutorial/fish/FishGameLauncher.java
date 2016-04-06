package org.magcruise.gaming.tutorial.fish;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.fish.resource.FishGameResourceLoader;

public class FishGameLauncher {

	public static void main(String[] args) {

		GameLauncher launcher = new GameLauncher(FishGameResourceLoader.class);
		launcher.addGameDefinitionInResource("fisherman.scm");
		// resourceLoader.addGameDefinitionInResource("fish.scm");
		launcher.addGameDefinitionInResource(
				"fish-game-by-interaction-protocol.scm");
		launcher.run();

	}

}
