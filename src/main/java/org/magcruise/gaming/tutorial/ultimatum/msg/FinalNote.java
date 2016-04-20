package org.magcruise.gaming.tutorial.ultimatum.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class FinalNote extends GameMessage {

	public final int proposition;

	public FinalNote(Symbol from, Symbol to, int proposition) {
		super(from, to);
		this.proposition = proposition;
	}

	@Override
	public Object[] getConstractorArgs() {
		return new Object[] { from, to, proposition };
	}

}
