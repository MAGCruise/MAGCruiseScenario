package org.magcruise.gaming.tutorial.fish;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class FishGameLauncher {

	public static void main(String[] args) {

		ResourceLoader loader = new org.magcruise.gaming.tutorial.fish.resource.ResourceLoader();
		loader.addGameDefinitionInResource("fisherman.scm");
		// resourceLoader.addGameDefinitionInResource("fish.scm");
		loader.addGameDefinitionInResource(
				"fish-game-by-interaction-protocol.scm");
		GameLauncher launcher = new GameLauncher(loader);
		launcher.run();

	}

}
