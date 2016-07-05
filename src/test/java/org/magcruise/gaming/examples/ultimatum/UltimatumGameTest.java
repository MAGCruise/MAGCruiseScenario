package org.magcruise.gaming.examples.ultimatum;

import org.apache.logging.log4j.Level;
import org.junit.Test;
import org.magcruise.gaming.examples.TestUtils;
import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameSession;
import org.magcruise.gaming.util.SystemEnvironmentUtils;
import org.nkjmlab.util.db.H2Client;

import gnu.kawa.io.Path;

public class UltimatumGameTest {

	private Integer[] firstPlayerAccounts = new Integer[] { 90000, 170000,
			170000, 230000, 280000, 280000, 370000, 450000, 450000, 510000,
			510000 };
	private Integer[] secondPlayerAccounts = new Integer[] { 10000, 30000,
			30000, 70000, 120000, 120000, 130000, 150000, 150000, 190000,
			190000 };

	private H2Client util = SystemEnvironmentUtils.getDefaultH2Client();

	@Test
	public void testRun() {
		GameSession launcher = new GameSession(
				UltimatumGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.setLogConfiguration(Level.INFO, true);
		launcher.useAutoInput();
		ProcessId pid = launcher.startAndWaitForFinish();
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
								+ "ORG_MAGCRUISE_GAMING_EXAMPLES_ULTIMATUM_ACTOR_FIRSTPLAYER"
								+ " WHERE PID=? ORDER BY ROUNDNUM",
						pid.toString()).toArray());

	}

	private void checkSecondPlayerResult(ProcessId pid,
			Object[] secondPlayerAccounts, int fromIndex, int toIndex) {
		TestUtils.checkResult(secondPlayerAccounts, fromIndex, toIndex,
				util.readList(int.class,
						"SELECT " + "ACCOUNT" + " FROM "
								+ "ORG_MAGCRUISE_GAMING_EXAMPLES_ULTIMATUM_ACTOR_SECONDPLAYER"
								+ " WHERE PID=? ORDER BY ROUNDNUM",
						pid.toString()).toArray());

	}

	@Test
	public void testRevert() {
		int suspendround = 4;
		GameSession revLauncher = new GameSession(
				UltimatumGameResourceLoader.class);
		revLauncher.addGameDefinitionInResource("game-definition.scm");
		revLauncher.addGameDefinitionInResource("test-definition.scm");
		revLauncher.useAutoInput();
		revLauncher.setFinalRound(suspendround);
		revLauncher.startAndWaitForFinish();
		Path revertCode = revLauncher.getRevertScriptPath();
		GameSession launcher = new GameSession(
				UltimatumGameResourceLoader.class);
		launcher.setBootstrapInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("test-definition.scm");
		launcher.addGameDefinition(revertCode);
		launcher.useAutoInput();
		ProcessId pid = launcher.startAndWaitForFinish();
		checkFirstPlayerResult(pid, firstPlayerAccounts, suspendround + 1,
				firstPlayerAccounts.length);
		checkSecondPlayerResult(pid, secondPlayerAccounts, suspendround + 1,
				secondPlayerAccounts.length);

	}

}
