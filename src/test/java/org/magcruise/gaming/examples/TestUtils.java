package org.magcruise.gaming.examples;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.manager.InternalGameProcess;
import org.magcruise.gaming.manager.ProcessId;
import org.magcruise.gaming.model.sys.GameLauncher;
import org.magcruise.gaming.model.sys.GameOnExternalProcessLauncher;
import org.magcruise.gaming.model.sys.GameOnLocalWithSeverLauncher;
import org.magcruise.gaming.model.sys.GameOnServerLauncher;

public class TestUtils {
	public static transient Logger log = org.apache.logging.log4j.LogManager
			.getLogger();

	public static void checkResult(Object[] expected, int fromIndex,
			int toIndex, Object[] actual) {
		Object[] expectedSub = Arrays.asList(expected)
				.subList(fromIndex, toIndex).toArray();
		try {
			assertEquals(expectedSub.length, actual.length);
			for (int i = 0; i < actual.length - 1; i++) {
				assertEquals(expectedSub[i], actual[i]);
			}
			log.debug("expected is {}.", Arrays.asList(expected).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
			log.debug("actual is {}.", Arrays.asList(actual).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
		} catch (Throwable e) {
			log.error(e, e);
			log.error("expected is {}.", Arrays.asList(expected).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
			log.error("actual is {}.", Arrays.asList(actual).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
			throw e;
		}

	}

	public static ProcessId run(GameLauncher launcher) {
		launcher.useAutoInput();
		InternalGameProcess p = launcher.run();
		while (!p.isFinished()) {

		}
		return launcher.getProcessId();
	}

	/**
	 * @param launcher
	 * @return
	 */
	public static ProcessId run(GameOnLocalWithSeverLauncher launcher) {
		launcher.useAutoInput();
		InternalGameProcess p = launcher.run();
		while (!p.isFinished()) {

		}
		return launcher.getProcessId();
	}

	public static ProcessId run(GameOnExternalProcessLauncher launcher) {
		launcher.useAutoInput();
		launcher.run();
		return launcher.getProcessId();
	}

	public static ProcessId run(GameOnServerLauncher launcher) {
		launcher.useAutoInput();
		return launcher.run();
	}

}
