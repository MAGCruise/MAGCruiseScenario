package org.magcruise.gaming.tutorial.ele;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class EleGameLauncher {

	public static void main(String[] args) {
		ResourceLoader loader = new org.magcruise.gaming.tutorial.ele.resource.ResourceLoader();
		loader.setBootstrapInResource("bootstrap.scm");
		GameLauncher launcher = new GameLauncher(loader);
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
