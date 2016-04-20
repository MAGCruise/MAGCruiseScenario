package org.magcruise.gaming.tutorial.croquette.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class PotatoDelivery extends GameMessage {

	public final int num;

	public PotatoDelivery(Symbol from, Symbol to, int orderOfPotato) {
		super(from, to);
		this.num = orderOfPotato;
	}

	@Override
	public Object[] getConstractorArgs() {
		return new Object[] { from, to, num };
	}

}
