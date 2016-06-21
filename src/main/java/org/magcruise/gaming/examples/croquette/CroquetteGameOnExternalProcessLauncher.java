package org.magcruise.gaming.examples.croquette;

import org.apache.logging.log4j.Level;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.model.sys.GameOnExternalProcessLauncher;

public class CroquetteGameOnExternalProcessLauncher {

	public static void main(String[] args) {
		GameOnExternalProcessLauncher launcher = new GameOnExternalProcessLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		// launcher.useSwingGui();
		launcher.useAutoInput();
		launcher.useRemoteDebug();
		launcher.setLogConfiguration(Level.INFO, true);

		launcher.run();

	}

}
