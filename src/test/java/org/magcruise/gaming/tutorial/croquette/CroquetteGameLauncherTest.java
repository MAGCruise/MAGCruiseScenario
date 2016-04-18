package org.magcruise.gaming.tutorial.croquette;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.manager.InternalGameProcess;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.sys.DefNoGui;
import org.magcruise.gaming.model.def.sys.GameSystemPropertiesBuilder;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;
import org.nkjmlab.util.rdb.RDBUtil;

public class CroquetteGameLauncherTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMain() {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.addDefUI(new DefNoGui());
		launcher.setAutoInputMode(true);
		InternalGameProcess p = launcher.run();
		while (!p.isFinished()) {

		}
		ProcessId pid = p.getExecutor().getProcessId();

		RDBUtil util = new RDBUtil("jdbc:h2:tcp://localhost/"
				+ GameSystemPropertiesBuilder.createDBFilePath().toString());
		{
			String actual = util.readList(int.class,
					"SELECT PROFIT FROM ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_CROQUETTEFACTORY "
							+ " WHERE PID=? ORDER BY ROUNDNUM",
					pid.toString()).toString();
			assertEquals(
					"[0, -6000, -70, 5990, 29990, -29940, -4000, 20000, 11000, 38000, 0]",
					actual);
		}

		{
			String actual = util.readList(int.class,
					"SELECT PROFIT FROM ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_SHOP "
							+ " WHERE PID=? AND PLAYER_NAME=? ORDER BY ROUNDNUM",
					pid.toString(), "Shop1").toString();
			assertEquals(
					"[22600, 27200, 7980, 9600, 3890, 7980, 12000, 4400, 14200, 12900, 0]",
					actual);
		}

		{
			String actual = util.readList(int.class,
					"SELECT PROFIT FROM ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_SHOP "
							+ " WHERE PID=? AND PLAYER_NAME=? ORDER BY ROUNDNUM",
					pid.toString(), "Shop2").toString();
			assertEquals(
					"[26400, 24000, 9240, 12800, 5940, 5360, 7700, 9700, 10600, 5900, 0]",
					actual);
		}

	}

}
