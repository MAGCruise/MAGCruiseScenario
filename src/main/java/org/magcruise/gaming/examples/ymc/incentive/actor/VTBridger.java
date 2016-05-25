package org.magcruise.gaming.examples.ymc.incentive.actor;

import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;

public class VTBridger extends Player {

	public volatile String revisedSentence = "";

	public VTBridger(PlayerParameter playerParameter) {
		super(playerParameter);
	}
}
