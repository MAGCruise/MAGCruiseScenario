package org.magcruise.gaming.tutorial.croquette;

import java.util.List;

import org.apache.logging.log4j.Logger;
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

import gnu.kawa.io.Path;

public class CroquetteGameLauncherTest {

	public static transient Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	@Before
	public void setUp() throws Exception {
	}

	private static Integer[] factoryProfits = new Integer[] { 0, -6000, -70,
			5990, 29990, -29940, -4000, 20000, 11000, 38000, 0 };

	private static Integer[] shop1Profits = new Integer[] { 22600, 27200, 7980,
			9600, 3890, 7980, 12000, 4400, 14200, 12900, 0 };
	private static Integer[] shop2Profits = new Integer[] { 26400, 24000, 9240,
			12800, 5940, 5360, 7700, 9700, 10600, 5900, 0 };
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
		ProcessId pid = TestUtils.exec(launcher);
		checkRunResult(pid);
	}

	protected static void checkRunResult(ProcessId pid) {
		checkFactoryResult(factoryProfits, pid, 0, factoryProfits.length);
		checkShopResult(shop1Profits, "Shop1", pid, 0, shop1Profits.length);
		checkShopResult(shop2Profits, "Shop2", pid, 0, shop2Profits.length);
	}

	private static void checkShopResult(Object[] expected, String name,
			ProcessId pid, int fromIndex, int toIndex) {
		List<Integer> actual = util.readList(int.class,
				"SELECT PROFIT FROM "
						+ " ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_SHOP "
						+ " WHERE PID=?  AND PLAYER_NAME=? ORDER BY ROUNDNUM",
				pid.toString(), name);
		TestUtils.checkResult(expected, fromIndex, toIndex, actual.toArray());
	}

	private static void checkFactoryResult(Object[] expected, ProcessId pid,
			int fromIndex, int toIndex) {
		List<Integer> actual = util.readList(int.class,
				"SELECT PROFIT FROM "
						+ " ORG_MAGCRUISE_GAMING_TUTORIAL_CROQUETTE_ACTOR_CROQUETTEFACTORY "
						+ " WHERE PID=? ORDER BY ROUNDNUM",
				pid.toString());
		TestUtils.checkResult(expected, fromIndex, toIndex, actual.toArray());
	}

	@Test
	public void testRevert() {
		GameLauncher launcher = new GameLauncher(
				CroquetteGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useAutoInput();
		int suspendround = 4;
		Path revertCode = launcher.runAndGetRevertCode(suspendround);
		launcher = new GameLauncher(CroquetteGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.addGameDefinition(revertCode);
		ProcessId pid = TestUtils.exec(launcher);
		checkRevertResult(pid, suspendround);
	}

	protected static void checkRevertResult(ProcessId pid, int suspendround) {
		checkFactoryResult(factoryProfits, pid, suspendround + 1,
				factoryProfits.length);
		checkShopResult(shop1Profits, "Shop1", pid, suspendround + 1,
				shop1Profits.length);
		checkShopResult(shop2Profits, "Shop2", pid, suspendround + 1,
				shop2Profits.length);
	}

}
