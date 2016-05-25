package org.magcruise.gaming.examples.ymc.incentive.actor;

import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;

public class JPBridger extends Player {

	public volatile String revisedSentence = "";

	public JPBridger(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void init() {

	}

}
