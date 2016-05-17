package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameOnExternalProcessLauncher;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;

public class CroquetteGameOnExternalProcessLauncher {

	public static void main(String[] args) {
		GameOnExternalProcessLauncher launcher = new GameOnExternalProcessLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useSwingGui();
		launcher.useAutoInput();
		launcher.run();

	}

}
