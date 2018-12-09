package org.magcruise.gaming.examples.discussion.actor;

import org.magcruise.gaming.examples.fish.resource.FishGameResourceLoader;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.message.ScenarioEvent;
import org.magcruise.gaming.ui.model.Form;
import org.magcruise.gaming.ui.model.attr.SimpleSubmit;
import org.magcruise.gaming.ui.model.input.TextInput;
import org.nkjmlab.util.langrid.AccessConfig;
import org.nkjmlab.util.langrid.client.TranslationClient;

public class Talker extends Player {

	private TranslationClient client;

	public Talker(PlayerParameter playerParameter) {
		super(playerParameter);
		AccessConfig conf = AccessConfig.from(
				new FishGameResourceLoader().getResourceAsStream("langrid-conf.json"));
		//client = new TranslationClient("KyotoUJServer");
		client = new TranslationClient(conf, "GoogleTranslate");
	}

	public boolean isStartNegotiation(DiscussionRoom ctx, ScenarioEvent msg) {
		return msg.isNamed(toSymbol("start-negotiation"));
	}

	public boolean isFinish(DiscussionRoom ctx, ScenarioEvent msg) {
		return msg.isNamed(toSymbol("finish-negotiation"));
	}

	private String jpLabel = "あなたが相手に伝えたいことを入力して下さい．";
	private String zhLabel = null;

	public void negotiation(DiscussionRoom ctx, ScenarioEvent msg) {
		String label;
		if (name.toString().contains("Chinese")) {
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
					String text = param.getArgAsString(0);
					if (text.length() == 0) {
					} else {
						ctx.getPlayers()
								.forEach(player -> sendDiscussionMessage(ctx, player, text));
					}
					if (text.equalsIgnoreCase("END")) {
						ctx.getPlayers()
								.forEach(player -> sendEvent(
										new ScenarioEvent(name, player.getName(),
												toSymbol("finish-negotiation"))));

					} else {
						sendEvent(new ScenarioEvent(name, name, toSymbol("")));
					}
				});
	}

	private void sendDiscussionMessage(DiscussionRoom ctx, Player to, String text) {
		if (name.toString().contains("Japanese")) {
			if (to.getName().toString().contains("Japanese")) {
				ctx.showMessage(to.getName(),
						"<div class='alert alert-success'>" + name + ": " + text + "</div>");
			} else {
				ctx.showMessage(to.getName(), "<div class='alert alert-success'>" + name
						+ ": " + client.translate("ja", "zh", text) + "</div>");
			}
		} else {
			if (to.getName().toString().contains("Japanese")) {
				ctx.showMessage(to.getName(), "<div class='alert alert-success'>" + name
						+ ": " + client.translate("zh", "ja", text) + "</div>");
			} else {
				ctx.showMessage(to.getName(),
						"<div class='alert alert-success'>" + name + ": " + text + "</div>");
			}

		}
	}

}
