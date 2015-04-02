package org.magcruise.gaming.scenario.croquette.player;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

import java.util.HashMap;
import java.util.Map;

import org.magcruise.gaming.model.Attribute;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.Player;
import org.magcruise.gaming.scenario.croquette.msg.CroquetteOrder;

public class Factory extends Player {

	@Attribute(name = "価格")
	public int price = 60;

	@Attribute(name = "発注個数(じゃがいも)")
	public int orderOfPotato;
	@Attribute(name = "納品個数(じゃがいも)")
	public int deliveredPotato;
	@Attribute(name = "生産個数")
	public int production;
	@Attribute(name = "在庫個数")
	public int stock;
	@Attribute(name = "加工費")
	public int machiningCost;
	@Attribute(name = "材料費")
	public int materialCost;
	@Attribute(name = "在庫費")
	public int inventoryCost;
	@Attribute(name = "売上個数")
	public int sales;
	@Attribute(name = "売上高")
	public int earnings;
	@Attribute(name = "利益")
	public int profit;
	@Attribute(name = "納品希望数")
	public int demand;

	@Attribute(name = "受注内容")
	public Map<Symbol, Integer> orders = new HashMap<>();

	public int[] defaultOrdersToFarmer;

	public Factory(Symbol playerName, Symbol playerType, int[] ordersToFarmer) {
		super(playerName, playerType);
		this.defaultOrdersToFarmer = ordersToFarmer;
		this.stock = 0;
		this.demand = 0;
		this.sales = 0;

	}

	public void receiveOrder(CroquetteOrder msg) {
		this.orders.put(msg.from, msg.num);
	}

	public int getTotalOrder(Context ctx) {
		if (ctx.roundnum < 2) {
			return 0;
		}

		Map<Symbol, Integer> prevOrders = (Map<Symbol, Integer>) prev(2,
				new SimpleSymbol("orders"));
		int tmp = 0;
		for (int num : prevOrders.values()) {
			tmp += num;
		}
		return tmp;
	}

	public void order(int num) {
		this.orderOfPotato = num;
	}

	public int delivery(Context ctx, Symbol shop, int stockBeforeDelivery) {
		int totalOrder = getTotalOrder(ctx);

		int order = ctx.roundnum < 2 ? 0 : ((Map<Symbol, Integer>) prev(2,
				new SimpleSymbol("orders"))).get(shop);
		int delivery = totalOrder <= stockBeforeDelivery ? order : (int) Math
				.floor(stockBeforeDelivery * (order / totalOrder));
		this.stock -= delivery;
		this.demand += order;
		this.sales += delivery;
		return delivery;
	}

	public void receiveDeliveryAndProduce(int potato) {
		this.deliveredPotato = potato;
		this.production = deliveredPotato * 2; // 1つのじゃがいもからコロッケが1つ
		this.stock += production;
	}

	public void closing() {
		this.inventoryCost = (stock - production) * 5; // 1つストックするのに5円かかる．今回の生産物には在庫費はかからない．
		this.materialCost = deliveredPotato * 10; // 材料費(じゃがいも購入費1つ20円)
		this.machiningCost = +production * 5; // コロッケ加工費1つ10円
		this.earnings = sales * price; // 収入は個数×単価
		this.profit = earnings - (materialCost + machiningCost + inventoryCost);// 利益は，収入から材料費，加工費，在庫維持費を引いたもの．
	}
}
