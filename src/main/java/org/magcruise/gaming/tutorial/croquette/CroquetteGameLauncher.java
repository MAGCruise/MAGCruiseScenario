package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useSwingGui();
		launcher.useAutoInput();
		launcher.runOnExternalProcess();

	}

}
