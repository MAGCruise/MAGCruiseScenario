package org.magcruise.gaming.examples.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.message.Alert;

public class SecondPlayer extends UltimatumPlayer {

	public List<Boolean> defaultYesOrNos;

	public SecondPlayer(PlayerParameter playerParameter,
			List<Boolean> yesOrNos) {
		super(playerParameter);
		this.defaultYesOrNos = new ArrayList<>(yesOrNos);
	}

	@Override
	public SConstructor<? extends Player> toConstructor(ToExpressionStyle style) {
		return SConstructor.toConstructor(style, getClass(), getPlayerParameter(), defaultYesOrNos);
	}

	public void beforeNegotiation(UltimatumGameContext ctx) {
		showAlertMessage(Alert.INFO, "相手からの通牒を待っています．");
	}

	public String getDefaultYesOrNo(int roundnum) {
		if (defaultYesOrNos.get(roundnum)) {
			return "yes";
		}
		return "no";
	}

	public void judge(UltimatumGameContext ctx) {
		syncRequestToInput(ctx.createForm("judge-form", ctx, this, takeMessage()),
				params -> {
					this.yesOrNo = params.getArgAsString(0);
					showAlertMessage(Alert.INFO, this.yesOrNo + "と答えました．");
				}, e -> {
					showAlertMessage(Alert.DANGER, e.getMessage());
					judge(ctx);
				});

	}
}
