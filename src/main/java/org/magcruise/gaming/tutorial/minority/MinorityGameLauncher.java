package org.magcruise.gaming.tutorial.minority;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.minority.resource.MinorityGameResourceLoader;

public class MinorityGameLauncher {

	public static void main(String[] args) {

		GameLauncher launcher = new GameLauncher(
				MinorityGameResourceLoader.class);
		launcher.addGameDefinitionInResource("minority-game.scm");
		launcher.run();

	}

}
