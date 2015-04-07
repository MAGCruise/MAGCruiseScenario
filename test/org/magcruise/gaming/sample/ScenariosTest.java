package org.magcruise.gaming.sample;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.magcruise.gaming.ui.swing.ReplLauncher;

public class ScenariosTest {
	private static ScheduledExecutorService gameExecutor;

	public static void main(String[] args) {
		gameExecutor = Executors.newSingleThreadScheduledExecutor();

		String[] scenarios = { "fish-game/fish.scm",
				"fish-game/fish-event-driven.scm",
				"ymc-gaming-2013-11/ymc.scm",
				"minority-game/minority-game.scm",
				"ultimatum-game/ultimatum-game.scm", "misc/gui-test.scm",
				"misc/langrid-invoker-test.scm",
				"croquette/croquette_single_x_6.scm",
				"croquette/croquette_single_x_3_and_factory_x_1.scm",
				"croquette/croquette_multi_x_3.scm",
				"croquette/croquette_multi_x_3_item_x_2.scm",
				"croquette/croquette_multi_x_4_item_x_2.scm" };
		for (String scenario : scenarios) {
			ReplLauncher sl = new ReplLauncher("../MAGCruiseCore", scenario);
			gameExecutor.scheduleAtFixedRate(sl, 1, 10, TimeUnit.SECONDS);
		}
	}
}
