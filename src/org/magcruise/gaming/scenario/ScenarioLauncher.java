package org.magcruise.gaming.scenario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.magcruise.gaming.lang.SExpression;
import org.magcruise.gaming.ui.swing.GameExecutorLauncherWithSwingGui;

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
		String scenario = "./scenario/otsuka-2015/otsuka.scm";

		// GameExecutorLauncherWithSwingGui launcher = new
		// GameExecutorLauncherWithSwingGui(
		// "../MAGCruiseCore", "./", toSExpression(scenario));

		GameExecutorLauncherWithSwingGui launcher = new GameExecutorLauncherWithSwingGui(
				"../MAGCruiseCore", "./", new SExpression(
						"(load \"./scenario/otsuka-2015/otsuka.scm\")"));

		// launcher.setInitScript("scenario/croquette/init.scm");

		launcher.runInExternalProcess();
		// launcher.runInSameProcess();
	}

	private static SExpression toSExpression(String scenario) {

		try {
			return new SExpression(String.join("\n",
					Files.readAllLines(new File(scenario).toPath())));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
