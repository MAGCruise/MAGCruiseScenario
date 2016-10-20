package org.magcruise.gaming.examples.discussion.actor;

import org.magcruise.gaming.examples.fish.resource.FishGameResourceLoader;
import org.magcruise.gaming.langrid.AccessConfigFactory;
import org.magcruise.gaming.langrid.client.TranslationClient;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.message.ScenarioEvent;
import org.magcruise.gaming.ui.model.Form;
import org.magcruise.gaming.ui.model.attr.SimpleSubmit;
import org.magcruise.gaming.ui.model.input.TextInput;

public class Talker extends Player {

	private TranslationClient client;

	public Talker(PlayerParameter playerParameter) {
		super(playerParameter);
		AccessConfigFactory.setPath(
				new FishGameResourceLoader().getResource("langrid-conf.json"));
		//client = new TranslationClient("KyotoUJServer");
		client = new TranslationClient("GoogleTranslate");
	}

	public boolean isStartNegotiation(DiscussionRoom ctx, ScenarioEvent msg) {
		return msg.isNamed(toSymbol("start-negotiation"));
	}

	public boolean isFinish(DiscussionRoom ctx, ScenarioEvent msg) {
		return msg.isNamed(toSymbol("finish-negotiation"));
	}

	private String jpLabel = "あなたが相手に伝えたいことを入力して下さい．もし，伝えたいことが何も無ければENDと入力して下さい．";
	private String zhLabel = null;

	public void negotiation(DiscussionRoom ctx, ScenarioEvent msg) {
		String label;
		if (name.toString()
				.equals(DiscussionRoom.CHINESE_DISCUSSANT.toString())) {
			if (zhLabel == null) {
				zhLabel = client.translate("ja", "zh", jpLabel);
			}
			label = zhLabel;
		} else {
			label = jpLabel;
		}

		syncRequestToInput(new Form("",
				new TextInput(label, "input-msg", "", new SimpleSubmit())),
				(param) -> {
					if (param.getArgAsString(0) == "") {
					} else if (name.toString()
							.equals(DiscussionRoom.JAPANESE_DISCUSSANT.toString())) {
						String text = param.getArgAsString(0);
						log.info(text);
						ctx.showMessage(DiscussionRoom.CHINESE_DISCUSSANT,
								"<div class='alert alert-success'>" + name
										+ ": " + client.translate("ja", "zh", text)
										+ "</div>");
						ctx.showMessage(DiscussionRoom.JAPANESE_DISCUSSANT,
								"<div class='alert alert-success'>" + name + ": " + text
										+ "</div>");
					} else {
						String text = param.getArgAsString(0);
						log.info(text);
						ctx.showMessage(DiscussionRoom.JAPANESE_DISCUSSANT,
								"<div class='alert alert-info'>" + name
										+ ": " + client.translate("zh", "ja", text)
										+ "</div>");
						ctx.showMessage(DiscussionRoom.CHINESE_DISCUSSANT,
								"<div class='alert alert-info'>" + name
										+ ": " + text + "</div>");

					}

					if (param.getArgAsString(0).equalsIgnoreCase("END")) {
						sendEvent(new ScenarioEvent(name, msg.from,
								toSymbol("finish-negotiation")));
						sendEvent(new ScenarioEvent(name, msg.to,
								toSymbol("finish-negotiation")));
					} else {
						sendEvent(new ScenarioEvent(name, name, toSymbol("")));
					}
				});
	}

}
