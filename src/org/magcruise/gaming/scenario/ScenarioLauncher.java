package org.magcruise.gaming.scenario;

import org.magcruise.gaming.util.GameProcessLauncher;

public class ScenarioLauncher {

	public static void main(String[] args) {
		// String scenario =
		// "scenario/ultimatum-game/ultimatum-game-children.scm";
		// String scenario = "scenario/croquette/croquette-game.scm";
		// String scenario = "scenario/ele/ele.scm";
		// String scenario = "scenario/fish-game/fish.scm";
		// String scenario =
		// "scenario/fish-game/fish-game-by-interaction-protocol.scm";
		// String scenario = "scenario/minority-game/minority-game.scm";
		// String scenario = "scenario/misc/gui-test.scm";
		// String scenario = "scenario/misc/langrid-invoker-test.scm";
		// String scenario = "scenario/misc/sample-workflow.scm";
		// String scenario = "scenario/ultimatum-game/ultimatum-game.scm";
		// String scenario =
		// "scenario/ultimatum-game/ultimatum-game-simple.scm";
		String scenario = "scenario/otsuka-2015/otsuka.scm";
		// String scenario = "scenario/ele/ele.scm";
		GameProcessLauncher launcher = new GameProcessLauncher(
				"../MAGCruiseCore", "./", scenario);

		// GameExecutorLauncherWithSwingGui launcher = new
		// GameExecutorLauncherWithSwingGui(
		// "../MAGCruiseCore", "./", new SExpression(
		// "(load \"./scenario/otsuka-2015/otsuka.scm\")"));

		// launcher.setInitScript("scenario/croquette/init.scm");

		// launcher.runOnExternalProcess();
		launcher.runOnCurrentProcess();
	}

}
