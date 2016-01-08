package org.magcruise.gaming.tutorial.trans;

import org.magcruise.gaming.model.sys.GameLauncher;

public class TranslationGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.trans.resource.ResourceLoader());
		l.run();

	}

}
