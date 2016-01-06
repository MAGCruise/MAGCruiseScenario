package org.magcruise.gaming.tutorial.scenario.croquette;

import org.magcruise.gaming.model.sys.GameLauncher;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.scenario.croquette.resource.ResourceLoader());
		// l.runOnExternalProcess();
		l.run();
	}

}
