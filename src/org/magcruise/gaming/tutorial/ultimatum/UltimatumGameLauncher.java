package org.magcruise.gaming.tutorial.ultimatum;

import org.magcruise.gaming.model.sys.GameLauncher;

public class UltimatumGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.ultimatum.resource.ResourceLoader());
		l.run();
	}

}
