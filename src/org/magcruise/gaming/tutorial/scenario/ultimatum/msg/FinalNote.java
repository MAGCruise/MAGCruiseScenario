package org.magcruise.gaming.tutorial.scenario.ultimatum.msg;

import org.magcruise.gaming.model.game.message.GameMessage;

import gnu.mapping.Symbol;

public class FinalNote extends GameMessage {

	public int proposition;

	public FinalNote(Symbol from, Symbol to, int proposition) {
		super(from, to);
		this.proposition = proposition;
	}

}
