package org.magcruise.gaming.examples.ymc.incentive;

import org.magcruise.gaming.examples.ymc.incentive.resource.YmcGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSession;

public class IncentiveGameLauncher {

	public static void main(String[] args) {
		GameSession launcher = new GameSession(YmcGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		// launcher.runOnExternalProcess();
		launcher.start();
	}

}
