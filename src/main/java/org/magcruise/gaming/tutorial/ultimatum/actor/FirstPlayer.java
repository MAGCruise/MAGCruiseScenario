package org.magcruise.gaming.tutorial.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.util.SExpressionUtils;

public class FirstPlayer extends UltPlayer {

	public List<Integer> defaultPropositions;

	public FirstPlayer(DefaultPlayerParameter playerParameter,
			List<Integer> propositions) {
		super(playerParameter);
		this.defaultPropositions = new ArrayList<>(propositions);
	}

	@Override
	public SConstructor<? extends Player> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(),
				toDefaultPlayerParameter(), defaultPropositions);
	}
}
