package org.magcruise.gaming.tutorial.croquette.actor;

import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.tutorial.croquette.msg.PotatoDelivery;
import org.magcruise.gaming.tutorial.croquette.msg.PotatoOrder;

public class Farmer extends Player {

	@HistoricalField
	public volatile int receivedOrderOfPotato = 0;

	public Farmer(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void receiveOrder(Market ctx) {
		takeAllMessages(PotatoOrder.class).forEach((msg) -> {
			receiveOrder(msg.num);
		});
	}

	public void receiveOrder(int order) {
		this.receivedOrderOfPotato = order;
	}

	public void refresh(Market ctx) {
		this.receivedOrderOfPotato = 0;
	}

	public void delivery(Market ctx) {
		int order = ctx.roundnum < 1 ? 0
				: Integer
						.valueOf(getLastValue(toSymbol("receivedOrderOfPotato"))
								.toString());
		sendGameMessage(new PotatoDelivery(name, toSymbol("Factory"), order));
	}

}
