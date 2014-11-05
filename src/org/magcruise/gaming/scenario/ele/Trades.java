package org.magcruise.gaming.scenario.ele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Trades implements Serializable {

	List<Trade> trades = new ArrayList<>();

	@Override
	public String toString() {
		return trades.toString();
	}

	public void add(Trade trade) {
		trades.add(trade);
	}
}