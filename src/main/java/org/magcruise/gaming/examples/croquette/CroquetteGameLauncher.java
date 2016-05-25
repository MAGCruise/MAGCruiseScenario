package org.magcruise.gaming.examples.croquette;

import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.useSwingGui();
		launcher.useAutoInput();
		launcher.run();

	}

}
