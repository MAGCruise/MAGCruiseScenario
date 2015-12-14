package org.magcruise.gaming.tutorial.scenario.croquette.player;

import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.tutorial.scenario.croquette.Market;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

import gnu.mapping.SimpleSymbol;

public class Farmer extends Player {

	@HistoricalField
	public Number receivedOrderOfPotato;

	public Farmer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void receiveOrder(int order) {
		this.receivedOrderOfPotato = order;
	}

	public void refresh() {
		this.receivedOrderOfPotato = 0;
	}

	public int delivery(Market ctx) {
		int order = ctx.roundnum < 1 ? 0
				: Integer.valueOf(
						getLastValue(new SimpleSymbol("receivedOrderOfPotato"))
								.toString());
		return order;
	}

}
