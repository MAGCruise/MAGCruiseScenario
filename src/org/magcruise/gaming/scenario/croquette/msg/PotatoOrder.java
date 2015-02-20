package org.magcruise.gaming.scenario.croquette.msg;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Message;

public class PotatoOrder extends Message {

	public int num;

	public PotatoOrder(Symbol name) {
		super(name);
	}

	public PotatoOrder(Symbol from, int orderOfPotato) {
		super(new SimpleSymbol(PotatoOrder.class.getSimpleName()), from);
		this.num = orderOfPotato;
	}
}
