package org.magcruise.gaming.scenario.croquette.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.magcruise.gaming.model.game.Attribute;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.scenario.croquette.msg.CroquetteOrder;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class Factory extends Player {

	@Attribute(name = "価格")
	public static int price = 60;

	@Attribute(name = "発注個数(じゃがいも)")
	public int orderOfPotato;
	@Attribute(name = "納品個数(じゃがいも)")
	public int deliveredPotato;
	@Attribute(name = "生産個数")
	public int production;
	@Attribute(name = "在庫個数")
	public int stock = 0;
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
	public Map<Symbol, Number> orders = new HashMap<>();

	@Attribute(name = "発注個数のデフォルト値")
	public List<Number> defaultOrdersToFarmer;

	public Factory(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public Factory(DefaultPlayerParameter playerParameter,
			List<Number> ordersToFarmer) {
		super(playerParameter);
		this.defaultOrdersToFarmer = ordersToFarmer;
	}

	public void refresh() {
		this.orderOfPotato = 0;
		this.deliveredPotato = 0;
		this.machiningCost = 0;
		this.materialCost = 0;
		this.inventoryCost = 0;
		this.sales = 0;
		this.earnings = 0;
		this.profit = 0;
		this.demand = 0;
		this.orders.clear();
	}

	public void receiveOrder(CroquetteOrder msg) {
		this.orders.put(msg.from, new Integer(msg.num));
	}

	public int getTotalOrder(Context ctx) {
		if (ctx.roundnum < 2) {
			return 0;
		}

		Map<Symbol, Number> prevOrders = (Map<Symbol, Number>) getValueBefore(
				new SimpleSymbol("orders"), 2);
		int tmp = 0;
		for (Number num : prevOrders.values()) {
			tmp += Integer.valueOf(num.toString());
		}
		return tmp;
	}

	public void order(int num) {
		this.orderOfPotato = num;
	}

	public int delivery(Context ctx, Symbol shop, int stockBeforeDelivery) {
		int totalOrder = getTotalOrder(ctx);

		int order = ctx.roundnum < 2 ? 0
				: Integer.valueOf(((Map<Symbol, Number>) getValueBefore(
						new SimpleSymbol("orders"), 2)).get(shop).toString());
		int delivery = totalOrder <= stockBeforeDelivery ? order
				: (int) Math.floor(stockBeforeDelivery * (order / totalOrder));
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
		this.inventoryCost = (stock - production) * 10; // 持ち越し在庫(=在庫量-生産量)*単価
		this.materialCost = deliveredPotato * 20; // 材料費(じゃがいも個数*単価)
		this.machiningCost = +production * 20; // 加工費(生産個数*単価)
		this.earnings = sales * price; // 収入は個数×単価
		this.profit = earnings - (materialCost + machiningCost + inventoryCost);// 利益は，収入から材料費，加工費，在庫維持費を引いたもの．
	}
}
