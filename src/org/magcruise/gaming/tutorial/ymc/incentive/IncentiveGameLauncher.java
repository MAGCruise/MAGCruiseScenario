package org.magcruise.gaming.tutorial.ymc.incentive;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.ResourceLoader;

public class IncentiveGameLauncher {

	public static void main(String[] args) {
		ResourceLoader loader = new org.magcruise.gaming.tutorial.ymc.incentive.resource.ResourceLoader();
		loader.setBootstrapInResource("bootstrap.scm");
		GameLauncher launcher = new GameLauncher(loader);
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
