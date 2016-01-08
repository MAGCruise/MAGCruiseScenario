package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		ResourceLoader loader = new org.magcruise.gaming.tutorial.croquette.resource.ResourceLoader();
		loader.setBootstrapInResource("bootstrap.scm");
		GameLauncher launcher = new GameLauncher(loader);
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
