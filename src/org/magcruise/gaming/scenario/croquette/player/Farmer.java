package org.magcruise.gaming.scenario.croquette.player;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.math.IntNum;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Attribute;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

public class Farmer extends Player {

	@Attribute
	public Number receivedOrderOfPotato;

	public Farmer(Symbol playerName, Symbol playerType, String operatorId,
			Properties props, MessageBox msgbox, History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}

	public void receiveOrder(int order) {
		this.receivedOrderOfPotato = order;
	}

	public void refresh() {
		this.receivedOrderOfPotato = 0;
	}

	public int delivery(Context ctx) {
		int order = ctx.roundnum < 1 ? 0 : Integer.valueOf(prev(1,
				new SimpleSymbol("receivedOrderOfPotato")).toString());

		return order;
	}
}
