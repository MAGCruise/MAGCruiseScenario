package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		// launcher.useSwingGui();
		launcher.useRoundValidation();
		launcher.useAutoInput();
		launcher.run();

	}

}
