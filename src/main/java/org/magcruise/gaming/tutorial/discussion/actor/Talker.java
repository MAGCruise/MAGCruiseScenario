package org.magcruise.gaming.tutorial.discussion.actor;

import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.SimpleContext;
import org.magcruise.gaming.model.game.message.ScenarioEvent;
import org.magcruise.gaming.ui.model.Form;
import org.magcruise.gaming.ui.model.attr.SimpleSubmit;
import org.magcruise.gaming.ui.model.input.TextInput;

public class Talker extends Player {

	public Talker(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public boolean isStartNegotiation(SimpleContext ctx, ScenarioEvent msg) {
		return msg.isNamed(toSymbol("start-negotiation"));
	}

	public boolean isFinish(SimpleContext ctx, ScenarioEvent msg) {
		return msg.isNamed(toSymbol("finish-negotiation"));
	}

	public void negotiation(SimpleContext ctx, ScenarioEvent msg) {
		syncRequestToInput(new Form("",
				new TextInput("", "input-msg", "END", new SimpleSubmit())),
				(param) -> {
					if (param.getArgAsString(0).equalsIgnoreCase("END")) {
						sendScenarioEvent(msg.from,
								toSymbol("finish-negotiation"));
						sendScenarioEvent(msg.to,
								toSymbol("finish-negotiation"));
					} else {
						sendScenarioEvent(name, toSymbol(""));
					}
				});
	}

}
