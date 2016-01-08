package org.magcruise.gaming.tutorial.ele.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Constraints {
	Map<String, Integer> constraints = new HashMap<>();

	public Constraints() {
		constraints.put("A-B", 25);
		constraints.put("B-A", 25);
	}

	public List<String> getTransferableAreasFrom(String from) {
		List<String> areas = new ArrayList<String>();
		areas.add(from);
		for (String line : constraints.keySet()) {
			if (line.split("-")[0].equals(from) && constraints.get(line) > 0) {
				areas.add(line.split("-")[1]);
			}
		}
		return areas;
	}

	public int getCapacity(String from, String to) {
		if (from.equals(to)) {
			return Integer.MAX_VALUE;
		}
		return constraints.get(from + "-" + to);
	}

	public void transfer(String from, String to, int amount) {
		if (from.equals(to)) {
			return;
		}
		int tmp = constraints.get(from + "-" + to) - amount;
		constraints.put(from + "-" + to, tmp);
		constraints.put(to + "-" + from, tmp);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
