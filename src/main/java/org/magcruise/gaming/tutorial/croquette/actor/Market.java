package org.magcruise.gaming.tutorial.croquette.actor;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;

public class Market extends Context {

	public Market(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	public int distributeDemand(Shop shop) {
		Shop other = getOther(shop);

		int demand = 0;

		if (shop.price < 105) {
			demand = 260;
		} else if (shop.price < 115) {
			demand = 250;
		} else if (shop.price < 130) {
			demand = 220;
		} else if (shop.price < 135) {
			demand = 200;
		} else if (shop.price < 155) {
			demand = 150;
		} else if (shop.price < 175) {
			demand = 120;
		} else if (shop.price <= 200) {
			demand = 70;
		} else {
			demand = 0;
		}

		if (shop.price < other.price) {
			demand += 100;// 安い金額を付けた方にボーナス
		} else if (shop.price == other.price) {
			demand += 50; // 同じ金額だったら等分
		}
		return demand;
	}

	public Shop getOther(Shop shop) {
		if (shop.getName().toString().equals("Shop1")) {
			return (Shop) getPlayer(toSymbol("Shop2"));
		} else {
			return (Shop) getPlayer(toSymbol("Shop1"));
		}
	}
}
