package org.magcruise.gaming.examples.fish.actor;

import org.magcruise.gaming.examples.fish.resource.FishGameResourceLoader;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.SimpleContext;
import org.magcruise.gaming.ui.model.Form;
import org.magcruise.gaming.ui.model.attr.SimpleSubmit;
import org.magcruise.gaming.ui.model.input.TextInput;
import org.nkjmlab.util.langrid.AccessConfig;
import org.nkjmlab.util.langrid.client.TranslationClient;

public class FishGamePlayer extends Player {

	public String text;

	public FishGamePlayer(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void negotiation(SimpleContext ctx) {
		AccessConfig conf = AccessConfig.from(
				new FishGameResourceLoader().getResourceAsStream("langrid-conf.json"));
		TranslationClient client = new TranslationClient(conf, "KyotoUJServer");

		syncRequestToInput(new Form("相手に伝えたいことを入力して下さい．何も無ければENDと入力して下さい．", new TextInput(
				"", "input-text", "", new SimpleSubmit())),
				(params) -> {
					this.text = params.getArgAsString(0);

					if (name.toString().equals("Player1")) {
						ctx.showMessageToAll(
								"<div class='alert alert-success'>" + name
										+ ": "
										+ client.translate("ja", "en",
												params.getArgAsString(0))
										+ "</div>");
					} else {
						ctx.showMessageToAll(
								"<div class='alert alert-info'>" + name + ": "
										+ client.translate("ja", "en",
												params.getArgAsString(0))
										+ "</div>");

					}
				});

	}

}
