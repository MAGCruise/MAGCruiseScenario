package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

import gnu.mapping.Symbol;

public class VTYouth extends Player {

	public String[] questions = { "乾田直播とは何ですか？", "菌はお米に害を与えますか？" };

	public String question = questions[0];

	boolean[] thanksMessageTimings = { true, false };
	boolean[] failureMessageTimings = { true, false };

	public VTYouth(Symbol playerName, Symbol playerType, String operatorId,
			Properties props, MessageBox msgbox, History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}
}
