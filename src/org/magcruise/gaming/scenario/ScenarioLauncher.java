package org.magcruise.gaming.scenario;

import java.io.File;
import java.nio.file.Path;

import org.magcruise.gaming.manager.GameProcessLauncher;
import org.magcruise.gaming.model.def.GameBuilder;

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
		// Path scenario = new File("scenario/otsuka-2015/otsuka.scm").toPath();
		Path scenario = new File("scenario/trans-2015/trans.scm").toPath();
		// String scenario = "scenario/cc-2015/ymc.scm";
		// String scenario = "scenario/ele/ele.scm";

		GameProcessLauncher launcher = new GameProcessLauncher(
				new GameBuilder().setGameDefinition(scenario));

		// launcher.runOnExternalProcess();

		launcher.runOnCurrentProcess();
	}

}
