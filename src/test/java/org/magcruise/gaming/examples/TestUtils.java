package org.magcruise.gaming.examples;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;

public class TestUtils {
	protected static Logger log = org.apache.logging.log4j.LogManager
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
			log.debug("expected length is {}.", Arrays.asList(expected).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
			log.debug("actual length is {}.", Arrays.asList(actual).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
		} catch (Throwable e) {
			log.error(e, e);
			log.error("expected is {}.", Arrays.asList(expectedSub).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
			log.error("actual is {}.", Arrays.asList(actual).stream()
					.map(o -> o.toString()).collect(Collectors.toList()));
			throw e;
		}

	}

}
