package org.magcruise.gaming.tutorial.ultimatum;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.magcruise.gaming.manager.InternalGameProcess;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.def.sys.GameSystemPropertiesBuilder;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.ultimatum.resource.UltimatumGameResourceLoader;
import org.nkjmlab.util.rdb.RDBUtil;

import gnu.kawa.io.Path;

public class UltimatumGameLauncherTest {

	private Integer[] frstplayerAccounts = new Integer[] { 90000, 170000,
			170000, 230000, 280000, 280000, 370000, 450000, 450000, 510000,
			510000 };
	private Integer[] secondPlayerAccounts = new Integer[] { 10000, 30000,
			30000, 70000, 120000, 120000, 130000, 150000, 150000, 190000,
			190000 };

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRun() {
		GameLauncher launcher = new GameLauncher(
				UltimatumGameResourceLoader.class);
		launcher.addGameDefinitionInResource("game-definition.scm");
		launcher.addGameDefinitionInResource("def-test-players.scm");
		checkResult(launcher, 0);

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
		checkResult(launcher, suspendround + 1);

	}

	private void checkResult(GameLauncher launcher, int roundnum) {
		launcher.useAutoInput();
		InternalGameProcess p = launcher.run();
		while (!p.isFinished()) {

		}
		ProcessId pid = p.getExecutor().getProcessId();

		RDBUtil util = new RDBUtil("jdbc:h2:tcp://localhost/"
				+ GameSystemPropertiesBuilder.createDBFilePath().toString());

		{
			String actual = util.readList(int.class,
					"SELECT " + "ACCOUNT" + " FROM "
							+ "ORG_MAGCRUISE_GAMING_TUTORIAL_ULTIMATUM_ACTOR_FIRSTPLAYER"
							+ " WHERE PID=? ORDER BY ROUNDNUM",
					pid.toString()).toString();
			assertEquals(Arrays.asList(frstplayerAccounts)
					.subList(roundnum, frstplayerAccounts.length).toString(),
					actual);
		}

		{
			String actual = util.readList(int.class,
					"SELECT " + "ACCOUNT" + " FROM "
							+ "ORG_MAGCRUISE_GAMING_TUTORIAL_ULTIMATUM_ACTOR_SECONDPLAYER"
							+ " WHERE PID=? ORDER BY ROUNDNUM",
					pid.toString()).toString();
			assertEquals(Arrays.asList(secondPlayerAccounts)
					.subList(roundnum, secondPlayerAccounts.length).toString(),
					actual);
		}

	}

}
