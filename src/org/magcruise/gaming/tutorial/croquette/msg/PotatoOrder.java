package org.magcruise.gaming.tutorial.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class PotatoOrder extends GameMessage {

	public int num;

	public PotatoOrder(Symbol from, Symbol to, int orderOfPotato) {
		super(from, to);
		this.num = orderOfPotato;
	}
}
