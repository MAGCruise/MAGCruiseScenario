package org.magcruise.gaming.tutorial.scenario.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class PotatoDelivery extends GameMessage {

	public int num;

	public PotatoDelivery(Symbol from, Symbol to, int orderOfPotato) {
		super(from, to);
		this.num = orderOfPotato;
	}
}
