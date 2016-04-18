package org.magcruise.gaming.tutorial.ymc.incentive.actor;

import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class VTBridger extends Player {

	public volatile String revisedSentence = "";

	public VTBridger(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}
}
