package org.magcruise.gaming.tutorial.scenario.trans;

import org.magcruise.gaming.developer.GameLauncher;

public class TranslationGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.scenario.trans.resource.ResourceLoader());
		// System.out.println(l.getRevertCodePath(4));
		// l.testTerminateAndRevert(7);
		// l.run();
		// l.runOnExternalProcess();
		l.run();
	}

}
