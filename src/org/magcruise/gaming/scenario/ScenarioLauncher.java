package org.magcruise.gaming.scenario;

import java.io.File;

import org.magcruise.gaming.ui.swing.LocalGUILauncher;

public class ScenarioLauncher {

	public static void main(String[] args) {
		// String scenario = "croquette/croquette-game.scm";
		// String scenario = "ele/ele.scm";
		// String scenario = "fish-game/fish.scm";
		// String scenario = "fish-game/fish-game-by-interaction-protocol.scm";
		// String scenario = "minority-game/minority-game.scm";
		// String scenario = "misc/gui-test.scm";
		// String scenario = "misc/langrid-invoker-test.scm";
		// String scenario = "misc/sample-workflow.scm";
		String scenario = "ultimatum-game/ultimatum-game.scm";
		// String scenario = "ultimatum-game/ultimatum-game-simple.scm";

		LocalGUILauncher launcher = new LocalGUILauncher(new File(
				"../MAGCruiseCore"), scenario);
		// launcher.setTestMode(false);

		launcher.run();
	}
}
