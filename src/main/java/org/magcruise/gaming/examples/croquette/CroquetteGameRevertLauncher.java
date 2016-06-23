package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class CroquetteGameRevertLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		// launcher.useSwingGui();
		launcher.useRoundValidation();
		launcher.useAutoInput();
		launcher.revertToEndOf("proc-20160623-134727-341", 4);
		launcher.run();

	}

}
