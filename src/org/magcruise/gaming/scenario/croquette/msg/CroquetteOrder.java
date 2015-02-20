package org.magcruise.gaming.scenario.croquette.msg;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Message;

public class CroquetteOrder extends Message {

	public int num;

	public CroquetteOrder(Symbol from, int num) {
		super(new SimpleSymbol(CroquetteOrder.class.getSimpleName()), from);
		this.num = num;
	}

}
