package org.magcruise.gaming.tutorial.ymc.incentive;

import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.ymc.incentive.resource.YmcGameResourceLoader;

public class IncentiveGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(YmcGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
