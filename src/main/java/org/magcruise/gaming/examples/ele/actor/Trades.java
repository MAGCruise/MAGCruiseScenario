package org.magcruise.gaming.examples.ele.actor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.magcruise.gaming.lang.SConstructive;
import org.magcruise.gaming.lang.SConstructor;

public class Trades implements SConstructive {

	private volatile List<Trade> trades = new CopyOnWriteArrayList<>();

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
	public SConstructor<? extends Trades> toConstructor(
			ToExpressionStyle style) {
		return SConstructor.toConstructor(style, getClass(), trades);
	}

	public List<Trade> getTrades() {
		return trades;
	}

}