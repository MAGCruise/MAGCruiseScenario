package org.magcruise.gaming.tutorial.scenario.ele.actor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.lang.SConstructive;
import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.util.SExpressionUtils;

public class Trades implements SConstructive {

	private List<Trade> trades = new ArrayList<>();

	public Trades() {
	}

	public Trades(List<Trade> trades) {
		this.trades.addAll(trades);
	}

	public void add(Trade trade) {
		trades.add(trade);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public SConstructor toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(), trades);
	}

	public List<Trade> getTrades() {
		return trades;
	}

}