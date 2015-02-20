package org.magcruise.gaming.scenario.croquette.msg;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Message;

public class CroquetteDelivery extends Message {

	public int num;

	public CroquetteDelivery(Symbol name) {
		super(name);
	}

	public CroquetteDelivery(Symbol from, int orderOfCroquette) {
		super(new SimpleSymbol(CroquetteDelivery.class.getSimpleName()), from);
		this.num = orderOfCroquette;
	}
}
