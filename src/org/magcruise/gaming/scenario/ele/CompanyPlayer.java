package org.magcruise.gaming.scenario.ele;

import gnu.mapping.Symbol;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Attribute;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

public class CompanyPlayer extends Player {

	@Attribute(name = "売り手/買い手")
	public String type;
	@Attribute(name = "エリア")
	public String area;
	@Attribute(name = "需要")
	public int demand;
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
		this.reservation = c.reservation;
		this.trades = new Trades();
	}

	public void bond(CompanyPlayer counterpart, int price, int amount) {
		this.trades.add(new Trade(counterpart.name, price, amount));
		this.demand -= amount;
	}

}
