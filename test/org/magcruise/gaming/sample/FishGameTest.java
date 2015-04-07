package org.magcruise.gaming.sample;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.ui.swing.LocalGUILauncher;

public class FishGameTest {

	@Before
	public void setUp() throws Exception {

		// String scenario = "fish-game/fish-event-driven.scm";
		// String scenario = "ymc-gaming-2013-11/ymc.scm";
		// String scenario = "minority-game/minority-game.scm";
		// String scenario = "ultimatum-game/ultimatum-game.scm";
		// String scenario = "misc/gui-test.scm";
		// String scenario = "misc/langrid-invoker-test.scm";
		// String scenario = "croquette/croquette_single_x_6.scm";
		// String scenario =
		// "croquette/croquette_single_x_3_and_factory_x_1.scm";
		// String scenario = "croquette/croquette_multi_x_3.scm";
		// String scenario =
		// "croquette/croquette_multi_x_3_item_x_2.scm";
		// String scenario = "croquette/croquette_multi_x_4_item_x_2.scm";

	}

	@Test
	public void testScenario() {
		String scenario = "fish-game/fish.scm";
		LocalGUILauncher sl = new LocalGUILauncher("../MAGCruiseCore/",
				scenario);
		sl.run();
	}

}
