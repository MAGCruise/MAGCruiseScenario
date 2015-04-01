package org.magcruise.gaming.scenario.croquette.player;

import gnu.mapping.Symbol;

import org.magcruise.gaming.model.Attribute;
import org.magcruise.gaming.model.Player;

public class Shop extends Player {

	@Attribute(name = "発注個数")
	public int order;

	@Attribute(name = "在庫個数")
	public int stock;
	@Attribute(name = "納品個数")
	public int delivery;
	@Attribute(name = "販売価格")
	public int price;
	@Attribute(name = "在庫費")
	public int inventoryCost;
	@Attribute(name = "材料費")
	public int materialCost;
	@Attribute(name = "売上個数")
	public int sales;
	@Attribute(name = "売上高")
	public int earnings;
	@Attribute(name = "利益")
	public int profit;
	@Attribute(name = "来店者数")
	public int demand;

	public int[] defaultOrders;
	public int[] defaultPrices;

	public Shop(Symbol playerName, Symbol playerType, int[] prices, int[] orders) {
		super(playerName, playerType);
		this.defaultPrices = prices;
		this.defaultOrders = orders;
		this.stock = 1000;
	}

	public void init() {
	}

	public void order(int orderOfCroquette) {
		this.order = orderOfCroquette;
	}

	public void price(int price) {
		this.price = price;
	}

	public void receiveDelivery(int delivery) {
		this.delivery = delivery;
		this.stock += delivery;
	}

	public void closing(int demand) {
		this.demand = demand;

		this.sales = (stock >= demand) ? demand : stock; // 需要だけ売れる．上限は在庫量．

		this.stock -= sales;
		this.earnings = sales * price; // 収入は売った個数*単価
		this.inventoryCost = stock * 5; // 在庫費は売った後に計算．1個5円
		this.materialCost = delivery * 60; // 材料費．冷凍コロッケの購入費は1個60円
		this.profit = earnings - (materialCost + inventoryCost); // 利益は収入から，材料費，在庫費を引いたもの．
	}
}