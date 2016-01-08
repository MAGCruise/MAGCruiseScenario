package org.magcruise.gaming.tutorial.scenario.ele;

import org.magcruise.gaming.model.sys.GameLauncher;

public class EleGameLauncher {

	public static void main(String[] args) {
		GameLauncher l = new GameLauncher(
				new org.magcruise.gaming.tutorial.scenario.ele.resource.ResourceLoader());
		// System.out.println(l.getRevertCodePath(4));
		// l.testTerminateAndRevert(7);
		// l.run();
		l.runOnExternalProcess();
		// l.run();

	}

}
