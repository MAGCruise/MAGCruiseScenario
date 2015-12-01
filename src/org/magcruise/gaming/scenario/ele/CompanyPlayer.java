package org.magcruise.gaming.scenario.ele;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.MainProperty;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.ui.model.Form;

public class CompanyPlayer extends Player {

	@MainProperty(name = "売手/買手")
	public String type;
	@MainProperty(name = "エリア")
	public String area;

	@MainProperty(name = "需要(初期)")
	public int demand;
	@MainProperty(name = "需要(残り)")
	public int remaindDemand;

	@MainProperty(name = "入力価格")
	public int inputPrice;

	@MainProperty(name = "留保価格")
	public int reservation;

	@MainProperty(name = "売買")
	public Trades trades;

	public CompanyPlayer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
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

	public void voteByHuman(MarketContext ctx, Form form) {
		syncRequestToInput(name, form, params -> {
			System.out.println(params);
		});

	}

	public void status(MarketContext ctx) {
		showMessage(tabulateHistory());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
