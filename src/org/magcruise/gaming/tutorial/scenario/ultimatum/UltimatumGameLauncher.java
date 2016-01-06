package org.magcruise.gaming.tutorial.scenario.ultimatum;

import org.magcruise.gaming.model.sys.GameLauncher;

public class UltimatumGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.scenario.ultimatum.resource.ResourceLoader());
		// System.out.println(l.getRevertCodePath(4));
		// l.testTerminateAndRevert(7);
		// l.run();
		// l.runOnExternalProcess();
		l.run();
	}

}
