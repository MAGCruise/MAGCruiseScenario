package org.magcruise.gaming.tutorial.croquette.actor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.tutorial.croquette.msg.CroquetteOrder;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class Factory extends Player {

	@HistoricalField(name = "価格")
	public static int price = 60;

	@HistoricalField(name = "発注個数(じゃがいも)")
	public int orderOfPotato;
	@HistoricalField(name = "納品個数(じゃがいも)")
	public int deliveredPotato;
	@HistoricalField(name = "生産個数")
	public int production;
	@HistoricalField(name = "在庫個数")
	public int stock = 0;
	@HistoricalField(name = "加工費")
	public int machiningCost;
	@HistoricalField(name = "材料費")
	public int materialCost;
	@HistoricalField(name = "在庫費")
	public int inventoryCost;
	@HistoricalField(name = "売上個数")
	public int sales;
	@HistoricalField(name = "売上高")
	public int earnings;
	@HistoricalField(name = "利益")
	public int profit;
	@HistoricalField(name = "納品希望数")
	public int demand;

	@HistoricalField(name = "受注内容")
	public Map<Symbol, Number> orders = new HashMap<>();

	@HistoricalField(name = "発注個数のデフォルト値")
	public List<Number> defaultOrdersToFarmer;

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
