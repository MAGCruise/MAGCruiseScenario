package org.magcruise.gaming.tutorial.ultimatum;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.sys.GameSystemPropertiesBuilder;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.TestUtils;
import org.magcruise.gaming.tutorial.ultimatum.resource.UltimatumGameResourceLoader;
import org.nkjmlab.util.db.DbClientFactory;
import org.nkjmlab.util.db.H2Client;
import org.nkjmlab.util.db.H2ConfigFactory;

import gnu.kawa.io.Path;

public class UltimatumGameLauncherTest {

	private Integer[] firstPlayerAccounts = new Integer[] { 90000, 170000,
			170000, 230000, 280000, 280000, 370000, 450000, 450000, 510000,
			510000 };
	private Integer[] secondPlayerAccounts = new Integer[] { 10000, 30000,
			30000, 70000, 120000, 120000, 130000, 150000, 150000, 190000,
			190000 };

	H2Client util = DbClientFactory.createH2Client(H2ConfigFactory
			.create("jdbc:h2:tcp://localhost/" + GameSystemPropertiesBuilder
					.createDefaultDBFilePath().toString()));

	@Before
	public void setUp() throws Exception {
		util.getConnection();
		System.out.println("test");
	}

	@Test
	public void testRun() {
		GameLauncher launcher = new GameLauncher(
				UltimatumGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		ProcessId pid = TestUtils.exec(launcher);
		checkFirstPlayerResult(pid, firstPlayerAccounts, 0,
				firstPlayerAccounts.length);
		checkSecondPlayerResult(pid, secondPlayerAccounts, 0,
				secondPlayerAccounts.length);

	}

	private void checkFirstPlayerResult(ProcessId pid,
			Object[] firstPlayerAccounts, int fromIndex, int toIndex) {
		TestUtils.checkResult(firstPlayerAccounts, fromIndex, toIndex,
				util.readList(int.class,
						"SELECT " + "ACCOUNT" + " FROM "
								+ "ORG_MAGCRUISE_GAMING_TUTORIAL_ULTIMATUM_ACTOR_FIRSTPLAYER"
								+ " WHERE PID=? ORDER BY ROUNDNUM",
						pid.toString()).toArray());

	}

	private void checkSecondPlayerResult(ProcessId pid,
			Object[] secondPlayerAccounts, int fromIndex, int toIndex) {
		TestUtils.checkResult(secondPlayerAccounts, fromIndex, toIndex,
				util.readList(int.class,
						"SELECT " + "ACCOUNT" + " FROM "
								+ "ORG_MAGCRUISE_GAMING_TUTORIAL_ULTIMATUM_ACTOR_SECONDPLAYER"
								+ " WHERE PID=? ORDER BY ROUNDNUM",
						pid.toString()).toArray());

	}

	@Test
	public void testRevert() {
		GameLauncher launcher = new GameLauncher(
				UltimatumGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.useAutoInput();
		int suspendround = 4;
		Path revertCode = launcher.runAndGetRevertCode(suspendround);
		launcher = new GameLauncher(UltimatumGameResourceLoader.class);
		launcher.setBootstrapInResource("bootstrap.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		launcher.addGameDefinition(revertCode);
		ProcessId pid = TestUtils.exec(launcher);
		checkFirstPlayerResult(pid, firstPlayerAccounts, suspendround + 1,
				firstPlayerAccounts.length);
		checkSecondPlayerResult(pid, secondPlayerAccounts, suspendround + 1,
				secondPlayerAccounts.length);

	}

}
