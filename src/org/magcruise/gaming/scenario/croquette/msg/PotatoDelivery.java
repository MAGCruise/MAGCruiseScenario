package org.magcruise.gaming.scenario.croquette.msg;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Message;

public class PotatoDelivery extends Message {

	public int num;

	public PotatoDelivery(Symbol name) {
		super(name);
	}

	public PotatoDelivery(Symbol from, int orderOfPotato) {
		super(new SimpleSymbol(PotatoDelivery.class.getSimpleName()), from);
		this.num = orderOfPotato;
	}
}
