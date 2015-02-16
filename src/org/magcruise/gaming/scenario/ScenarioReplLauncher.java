package org.magcruise.gaming.scenario;

import java.io.File;

import org.magcruise.gaming.ui.repl.ReplLauncher;

public class ScenarioReplLauncher {

	public static void main(String[] args) {
		// String scenario = "misc/sample-workflow.scm";
		// String scenario = "fish-game/fish.scm";
		// String scenario = "fish-game/fish-game-by-interaction-protocol.scm";
		// String scenario = "ymc-gaming-2013-11/ymc.scm";
		// String scenario = "minority-game/minority-game.scm";
		// String scenario = "ultimatum-game/ultimatum-game.scm";
		// String scenario = "ultimatum-game/ultimatum-game-for-exp.scm";
		// String scenario = "misc/gui-test.scm";
		// String scenario = "misc/langrid-invoker-test.scm";
		// String scenario = "croquette/croquette-game.scm";
		// String scenario = "ele/ele.scm";
		String scenario = "ymc-gaming-2014-11/ymc.scm";
		ReplLauncher sl = new ReplLauncher(new File("../MAGCruiseCore"),
				scenario);
		sl.run();
	}
}