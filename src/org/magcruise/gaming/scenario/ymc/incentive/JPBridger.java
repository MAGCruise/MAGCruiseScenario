package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

import gnu.mapping.Symbol;

public class JPBridger extends Player {

	public String revisedSentence = "";

	public JPBridger(Symbol playerName, Symbol playerType, String operatorId,
			Properties props, History history, MessageBox msgbox) {
		super(playerName, playerType, operatorId, props, history, msgbox);
	}

	public void init() {

	}

}
