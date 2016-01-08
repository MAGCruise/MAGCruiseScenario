package org.magcruise.gaming.tutorial.ele;

import org.magcruise.gaming.model.sys.GameLauncher;

public class EleGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.ele.resource.ResourceLoader());
		l.runOnExternalProcess();
		// l.run();

	}

}
