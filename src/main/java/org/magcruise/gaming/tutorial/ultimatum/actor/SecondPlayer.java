package org.magcruise.gaming.tutorial.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.Player;

public class SecondPlayer extends UltPlayer {

	public List<Boolean> defaultYesOrNos;

	public SecondPlayer(PlayerParameter playerParameter,
			List<Boolean> yesOrNos) {
		super(playerParameter);
		this.defaultYesOrNos = new ArrayList<>(yesOrNos);
	}

	@Override
	public SConstructor<? extends Player> toConstructor() {
		return SConstructor.toConstructor(this.getClass(),
				getPlayerParameter(), defaultYesOrNos);
	}

	public String getDefaultYesOrNo(int roundnum) {
		if (defaultYesOrNos.get(roundnum)) {
			return "yes";
		}
		return "no";
	}
}
