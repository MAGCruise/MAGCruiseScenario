package org.magcruise.gaming.tutorial;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.magcruise.gaming.manager.InternalGameProcess;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.tutorial.croquette.CroquetteGameLauncherTest;

public class TestUtils {

	public static void checkResult(Object[] expected, int fromIndex,
			int toIndex, Object[] actual) {
		Object[] expectedSub = Arrays.asList(expected)
				.subList(fromIndex, toIndex).toArray();
		try {
			assertEquals(expectedSub.length, actual.length);
			for (int i = 0; i < actual.length - 1; i++) {
				assertEquals(expectedSub[i], actual[i]);
			}
		} catch (Throwable e) {
			CroquetteGameLauncherTest.log.error(e, e);
			throw e;
		}
	}

	public static ProcessId exec(GameLauncher launcher) {
		launcher.useAutoInput();
		InternalGameProcess p = launcher.run();
		while (!p.isFinished()) {
	
		}
		return p.getExecutor().getProcessId();
	}

}
