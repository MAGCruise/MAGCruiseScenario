package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

import gnu.mapping.Symbol;

public class JPBridger extends Player {

	public String revisedSentence = "";

	public JPBridger(Symbol playerName, Symbol playerType, String operatorId, Properties props, MessageBox msgbox,
			History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}

	public void init() {

	}

}
