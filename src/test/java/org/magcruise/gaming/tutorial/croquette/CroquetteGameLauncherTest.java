package org.magcruise.gaming.tutorial.croquette;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.sys.GameSystemPropertiesBuilder;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.croquette.resource.CroquetteGameResourceLoader;
import org.nkjmlab.util.db.DbClientFactory;
import org.nkjmlab.util.db.H2Client;
import org.nkjmlab.util.db.H2ConfigFactory;

public class CroquetteGameLauncherTest {

	protected static transient org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Before
	public void setUp() throws Exception {
	}

	protected static Integer[] factoryProfits = new Integer[] { 0, -6000, -70,
			5990, 29990, -29940, -4000, 20000, 11000, 38000, 0 };

	protected static Integer[] shop1Profits = new Integer[] { 22600, 27200,
			7980, 9600, 3890, 7980, 12000, 4400, 14200, 12900, 0 };
	protected static Integer[] shop2Profits = new Integer[] { 26400, 24000,
			9240, 12800, 5940, 5360, 7700, 9700, 10600, 5900, 0 };
	protected static H2Client util = DbClientFactory
			.createH2Client(H2ConfigFactory.create(
					"jdbc:h2:tcp://localhost/" + GameSystemPropertiesBuilder
							.createDefaultDBFilePath().toString()));

	@Test
	public void testRun() {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		ProcessId pid = TestUtils.run(launcher);
		checkRunResult(pid);
	}

	protected static void checkRunResult(ProcessId pid) {
		checkFactoryResult(pid, factoryProfits, 0, factoryProfits.length);
		checkShopResult(pid, shop1Profits, "Shop1", 0, shop1Profits.length);
		checkShopResult(pid, shop2Profits, "Shop2", 0, shop2Profits.length);
	}

	protected static void checkShopResult(ProcessId pid, Object[] expected,
			String name, int fromIndex, int toIndex) {
		List<Integer> actual = util.readList(int.class,
				"SELECT PROFIT FROM "
						+ " ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_SHOP "
						+ " WHERE PID=?  AND PLAYER_NAME=? ORDER BY ROUNDNUM",
				pid.toString(), name);
		TestUtils.checkResult(expected, fromIndex, toIndex, actual.toArray());
	}

	protected static void checkFactoryResult(ProcessId pid, Object[] expected,
			int fromIndex, int toIndex) {
		List<Integer> actual = util.readList(int.class,
				"SELECT PROFIT FROM "
						+ " ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_CROQUETTEFACTORY "
						+ " WHERE PID=? ORDER BY ROUNDNUM",
				pid.toString());
		TestUtils.checkResult(expected, fromIndex, toIndex, actual.toArray());
	}

	protected static void checkResult(ProcessId pid, int finalRound) {
		int startRound = finalRound + 1;
		checkFactoryResult(pid, factoryProfits, startRound,
				factoryProfits.length);
		checkShopResult(pid, shop1Profits, "Shop1", startRound,
				shop1Profits.length);
		checkShopResult(pid, shop2Profits, "Shop2", startRound,
				shop2Profits.length);
	}

}
