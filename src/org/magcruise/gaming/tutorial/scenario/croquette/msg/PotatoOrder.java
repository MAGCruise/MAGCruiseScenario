package org.magcruise.gaming.tutorial.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.SimpleGameMessage;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class PotatoOrder extends SimpleGameMessage {

	public int num;

	public PotatoOrder(Symbol name) {
		super(name);
	}

	public PotatoOrder(Symbol from, int orderOfPotato) {
		super(new SimpleSymbol(PotatoOrder.class.getSimpleName()));
		this.from = from;
		this.num = orderOfPotato;
	}
}
