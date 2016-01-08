package org.magcruise.gaming.tutorial.croquette;

import org.magcruise.gaming.model.sys.GameLauncher;

public class CroquetteGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.croquette.resource.ResourceLoader());
		// l.runOnExternalProcess();
		l.run();
	}

}
