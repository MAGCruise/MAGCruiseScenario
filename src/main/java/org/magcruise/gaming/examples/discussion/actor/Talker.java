package org.magcruise.gaming.examples.discussion.actor;

import org.magcruise.gaming.examples.fish.resource.FishGameResourceLoader;
import org.magcruise.gaming.langrid.AccessConfigFactory;
import org.magcruise.gaming.langrid.client.TranslationClient;
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
		AccessConfigFactory.setPath(
				new FishGameResourceLoader().getResource("langrid-conf.json"));
		TranslationClient client = new TranslationClient("KyotoUJServer");

		syncRequestToInput(ctx,
				new Form("",
						new TextInput(
								"roundnum=" + ctx.getRoundnum()
										+ ". 相手に伝えたいことを入力して下さい．何も無ければENDと入力して下さい．",
								"input-msg", "", new SimpleSubmit())),
				(param) -> {

					if (name.toString().equals("Player1")) {
						ctx.showMessageToAll(
								"<div class='alert alert-success'>" + name
										+ ": "
										+ client.translate("ja", "en",
												param.getArgAsString(0))
										+ "</div>");
					} else {
						ctx.showMessageToAll(
								"<div class='alert alert-info'>" + name + ": "
										+ client.translate("ja", "en",
												param.getArgAsString(0))
										+ "</div>");

					}

					if (param.getArgAsString(0).equalsIgnoreCase("END")) {
						sendMessage(new ScenarioEvent(name, msg.from,
								toSymbol("finish-negotiation")));
						sendMessage(new ScenarioEvent(name, msg.to,
								toSymbol("finish-negotiation")));
					} else {
						sendMessage(
								new ScenarioEvent(name, name, toSymbol("")));
					}
				});
	}

}
