package org.magcruise.gaming.scenario.ele;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.lang.SConvertible;
import org.magcruise.gaming.util.SExpressionUtils;

public class Trades implements SConvertible {

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
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@Override
	public String toSExpression() {
		return SExpressionUtils.toSExpression(this, trades);
	}

}