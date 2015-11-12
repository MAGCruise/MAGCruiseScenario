package org.magcruise.gaming.scenario.croquette;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Players;
import org.magcruise.gaming.scenario.croquette.player.Shop;

import gnu.mapping.SimpleSymbol;

public class Market extends Context {

	public Market(int roundnum, Properties props, History history,
			MessageBox msgbox, Players players, String scenarioHome) {
		super(roundnum, props, history, msgbox, players, scenarioHome);
	}

	public int distributeDemand(Shop shop) {
		Shop other = getOther(shop);

		int demand = 0;

		if (shop.price < 60) {
			demand = 0;
		} else if (shop.price < 105) {
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
		if (shop.name.toString().equals("Shop1")) {
			return (Shop) getPlayer(new SimpleSymbol("Shop2"));
		} else {
			return (Shop) getPlayer(new SimpleSymbol("Shop1"));
		}
	}
}
