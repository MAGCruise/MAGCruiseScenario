package org.magcruise.gaming.scenario.ele;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Attribute;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

import gnu.mapping.Symbol;

public class CompanyPlayer extends Player {

	@Attribute(name = "売手/買手")
	public String type;
	@Attribute(name = "エリア")
	public String area;

	@Attribute(name = "需要(初期)")
	public int demand;
	@Attribute(name = "需要(残り)")
	public int remaindDemand;

	@Attribute(name = "入力価格")
	public int inputPrice;

	@Attribute(name = "留保価格")
	public int reservation;

	@Attribute(name = "売買")
	public Trades trades;

	public CompanyPlayer(Symbol playerName, Symbol playerType,
			String operatorId, Properties props, MessageBox msgbox,
			History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}

	public void init(CompanySetting c) {
		this.type = c.type;
		this.area = c.area;
		this.demand = c.demand;
		this.remaindDemand = c.demand;
		this.inputPrice = c.reservation;
		this.reservation = c.reservation;
		this.trades = new Trades();
	}

	public void bond(CompanyPlayer counterpart, int price, int amount) {
		this.trades.add(new Trade(counterpart.name, price, amount));
		this.remaindDemand -= amount;
	}

	public void vote(MarketContext ctx) {
		System.out.println("Agentの意志決定が書けます．");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
