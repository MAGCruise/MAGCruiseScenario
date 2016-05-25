package org.magcruise.gaming.examples.fish;

import org.magcruise.gaming.examples.fish.resource.FishGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class FishGameLauncher {

	public static void main(String[] args) {

		GameLauncher launcher = new GameLauncher(FishGameResourceLoader.class);
		launcher.addGameDefinitionInResource("fisherman.scm");
		// resourceLoader.addGameDefinitionInResource("fish.scm");
		launcher.addGameDefinitionInResource(
				"fish-game-by-interaction-protocol.scm");
		launcher.useSwingGui();
		launcher.run();

	}

}
