package org.magcruise.gaming.examples.minority;

import org.magcruise.gaming.examples.minority.resource.MinorityGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class MinorityGameLauncher {

	public static void main(String[] args) {

		GameLauncher launcher = new GameLauncher(
				MinorityGameResourceLoader.class);
		launcher.addGameDefinitionInResource("minority-game.scm");
		launcher.run();

	}

}
