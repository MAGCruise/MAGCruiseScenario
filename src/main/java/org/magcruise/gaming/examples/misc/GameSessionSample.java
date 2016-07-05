package org.magcruise.gaming.examples.misc;

import org.magcruise.gaming.model.game.ActorName;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.sys.GameSession;
import org.magcruise.gaming.ui.model.FormBuilder;
import org.magcruise.gaming.ui.model.attr.Max;
import org.magcruise.gaming.ui.model.attr.Min;
import org.magcruise.gaming.ui.model.attr.Required;
import org.magcruise.gaming.ui.model.input.NumberInput;

public class GameSessionSample {

	public static void main(String[] args) throws Throwable {
		String webUIUrl = "http://toho.magcruise.org/world/BackendAPIService";
		String brokerUrl = "http://proxy.phoenix.toho.magcruise.org/MAGCruiseBroker";

		GameSession session = new GameSession();
		session.setBroker(brokerUrl);
		session.setWebUI(webUIUrl, "admin", brokerUrl);
		ActorName playerName = ActorName.of("PlayerOne");
		session.setTasksFor(playerName);
		session.start();
		Context ctx = session.getContext();
		ctx.showMessage(playerName, "Test for show message.");

		FormBuilder formBuilder = new FormBuilder("お金出しますか？")
				.addInput(new NumberInput("共同基金への出資金額", "money", 1000,
						new Min(0), new Max(1000), new Required()));
		ctx.syncRequestToInput(playerName, formBuilder.build(), params -> {
			ctx.showMessage(playerName,
					"あなたの出資金額は" + params.getInt("money") + "円です");
		});
		session.finishTaskFor(playerName);

	}

}
