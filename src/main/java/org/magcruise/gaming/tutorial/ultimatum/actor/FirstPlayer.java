package org.magcruise.gaming.tutorial.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.Player;

public class FirstPlayer extends UltPlayer {

	public List<Integer> defaultPropositions;

	public FirstPlayer(PlayerParameter playerParameter,
			List<Integer> propositions) {
		super(playerParameter);
		this.defaultPropositions = new ArrayList<>(propositions);
	}

	@Override
	public SConstructor<? extends Player> toConstructor() {
		return SConstructor.toConstructor(this.getClass(),
				getPlayerParameter(), defaultPropositions);
	}
}
