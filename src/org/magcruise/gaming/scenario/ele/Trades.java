package org.magcruise.gaming.scenario.ele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Trades implements Serializable {

	List<Trade> trades = new ArrayList<>();

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public void add(Trade trade) {
		trades.add(trade);
	}
}