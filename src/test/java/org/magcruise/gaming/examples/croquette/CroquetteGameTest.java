package org.magcruise.gaming.examples.croquette;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameSession;
import org.magcruise.gaming.util.SystemEnvironmentUtils;
import org.nkjmlab.util.db.H2Client;

public class CroquetteGameTest {

	protected static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	public static Integer[] factoryProfits = new Integer[] { 0, -6000, -70,
			5990, 29990, -29940, -4000, 20000, 11000, 38000, 0 };

	public static Integer[] shop1Profits = new Integer[] { 22600, 27200, 7980,
			9600, 3890, 7980, 12000, 4400, 14200, 12900, 0 };
	public static Integer[] shop2Profits = new Integer[] { 26400, 24000, 9240,
			12800, 5940, 5360, 7700, 9700, 10600, 5900, 0 };

	protected static H2Client util = SystemEnvironmentUtils
			.getDefaultH2Client();

	public static String[] brokerUrls = {
			"http://localhost:8080/MAGCruiseBroker",
			"http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker" };

	@Test
	public void testRun() {
		GameSession session = new GameSession(
				CroquetteGameResourceLoader.class);
		session.addGameDefinitionInResource("game-definition.scm");
		session.addGameDefinitionInResource("test-definition.scm");
		session.setLogConfiguration(Level.INFO, true);
		session.useAutoInput();
		log.info(session.toDefBootstrap());
		ProcessId pid = session.startAndWaitForFinish();
		checkResult(pid);
	}

	protected static void checkResult(ProcessId pid) {
		log.debug("pid={}", pid);
		checkFactoryResult(pid, factoryProfits, 0, factoryProfits.length);
		checkShopResult(pid, shop1Profits, "Shop1", 0, shop1Profits.length);
		checkShopResult(pid, shop2Profits, "Shop2", 0, shop2Profits.length);
	}

	protected static void checkShopResult(ProcessId pid, Object[] expected,
			String name, int fromIndex, int toIndex) {
		List<Integer> actual = util.readList(int.class,
				"SELECT PROFIT FROM "
						+ " ORG_MAGCRUISE_GAMING_EXAMPLES_CROQUETTE_ACTOR_SHOP "
						+ " WHERE PID=?  AND PLAYER_NAME=? ORDER BY ROUNDNUM",
				pid.toString(), name);
		TestUtils.checkResult(expected, fromIndex, toIndex, actual.toArray());
	}

	protected static void checkFactoryResult(ProcessId pid, Object[] expected,
			int fromIndex, int toIndex) {
		List<Integer> actual = util.readList(int.class,
				"SELECT PROFIT FROM "
						+ " ORG_MAGCRUISE_GAMING_EXAMPLES_CROQUETTE_ACTOR_CROQUETTEFACTORY "
						+ " WHERE PID=? ORDER BY ROUNDNUM",
				pid.toString());
		TestUtils.checkResult(expected, fromIndex, toIndex, actual.toArray());
	}

	protected static void checkResult(ProcessId pid, int finalRound) {
		checkFactoryResult(pid, factoryProfits, finalRound + 1,
				factoryProfits.length);
		checkShopResult(pid, shop1Profits, "Shop1", finalRound + 1,
				shop1Profits.length);
		checkShopResult(pid, shop2Profits, "Shop2", finalRound + 1,
				shop2Profits.length);
	}

}
