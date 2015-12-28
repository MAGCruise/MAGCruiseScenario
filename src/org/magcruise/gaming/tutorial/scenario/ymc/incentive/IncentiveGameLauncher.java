package org.magcruise.gaming.tutorial.scenario.ymc.incentive;

import org.magcruise.gaming.developer.GameLauncher;
import org.magcruise.gaming.tutorial.scenario.ymc.incentive.resource.ResourceLoader;

public class IncentiveGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.scenario.ymc.incentive.resource.ResourceLoader());
		// l.runOnExternalProcess();
		l.run();
	}

}
