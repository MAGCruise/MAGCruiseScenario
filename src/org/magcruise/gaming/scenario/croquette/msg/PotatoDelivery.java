package org.magcruise.gaming.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.SimpleGameMessage;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class PotatoDelivery extends SimpleGameMessage {

	public int num;

	public PotatoDelivery(Symbol name) {
		super(name);
	}

	public PotatoDelivery(Symbol from, int orderOfPotato) {
		super(new SimpleSymbol(PotatoDelivery.class.getSimpleName()));
		this.from = from;
		this.num = orderOfPotato;
	}
}
