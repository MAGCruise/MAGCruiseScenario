package org.magcruise.gaming.tutorial.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.util.SExpressionUtils;

public class SecondPlayer extends UltPlayer {

	public List<Boolean> defaultYesOrNos;

	public SecondPlayer(DefaultPlayerParameter playerParameter,
			List<Boolean> yesOrNos) {
		super(playerParameter);
		this.defaultYesOrNos = new ArrayList<>(yesOrNos);
	}

	@Override
	public SConstructor<? extends Player> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(),
				toDefaultPlayerParameter(), defaultYesOrNos);
	}

	public String getDefaultYesOrNo(int roundnum) {
		if (defaultYesOrNos.get(roundnum)) {
			return "yes";
		}
		return "no";
	}
}
