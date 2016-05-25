package org.magcruise.gaming.examples.ymc.incentive;

import org.magcruise.gaming.examples.ymc.incentive.resource.YmcGameResourceLoader;
import org.magcruise.gaming.model.sys.GameLauncher;

public class IncentiveGameLauncher {

	public static void main(String[] args) {
		GameLauncher launcher = new GameLauncher(YmcGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.runOnExternalProcess();
		launcher.run();
	}

}
