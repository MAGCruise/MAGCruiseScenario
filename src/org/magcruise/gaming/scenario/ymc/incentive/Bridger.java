package org.magcruise.gaming.scenario.ymc.incentive;

import gnu.mapping.Symbol;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

public class Bridger extends Player {

	public String revisedSentence = "";

	public Bridger(Symbol playerName, Symbol playerType) {
		super(playerName, playerType);
	}

	public Bridger(Symbol playerName, Symbol playerType, String operatorId,
			Properties props, MessageBox msgbox, History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}
}
