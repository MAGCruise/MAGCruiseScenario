package org.magcruise.gaming.tutorial.fish;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class FishGameLauncher {

	public static void main(String[] args) {

		ResourceLoader resourceLoader = new org.magcruise.gaming.tutorial.fish.resource.ResourceLoader();
		resourceLoader.addGameDefinitionInResource("fisherman.scm");
		// resourceLoader.addGameDefinitionInResource("fish.scm");
		resourceLoader.addGameDefinitionInResource(
				"fish-game-by-interaction-protocol.scm");
		GameLauncher l = new GameLauncher(resourceLoader);
		l.run();

	}

}
