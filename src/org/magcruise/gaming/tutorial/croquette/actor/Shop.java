package org.magcruise.gaming.tutorial.croquette.actor;

import java.util.List;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;

public class Shop extends Player {

	@HistoricalField(name = "在庫個数")
	public int stock;

	@HistoricalField(name = "発注個数")
	public int numOfOrder;
	@HistoricalField(name = "納品個数")
	public int delivery;
	@HistoricalField(name = "販売価格")
	public int price;
	@HistoricalField(name = "在庫費")
	public int inventoryCost;
	@HistoricalField(name = "仕入費")
	public int materialCost;
	@HistoricalField(name = "売上個数")
	public int sales;
	@HistoricalField(name = "売上高")
	public int earnings;
	@HistoricalField(name = "利益")
	public int profit;
	@HistoricalField(name = "来店者数")
	public int demand;

	// @Attribute(name = "発注個数のデフォルト値")
	public List<Number> defaultOrders;
	// @Attribute(name = "販売価格のデフォルト値")
	public List<Number> defaultPrices;

	public Shop(DefaultPlayerParameter playerParameter, List<Number> prices,
			List<Number> orders) {
		super(playerParameter);
		this.stock = 600;
		this.defaultPrices = prices;
		this.defaultOrders = orders;
	}

	public void refresh() {
		this.numOfOrder = 0;
		this.delivery = 0;
		this.price = 0;
		this.inventoryCost = 0;
		this.materialCost = 0;
		this.sales = 0;
		this.earnings = 0;
		this.profit = 0;
		this.demand = 0;
	}

	public void order(int orderOfCroquette) {
		this.numOfOrder = orderOfCroquette;
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
		this.inventoryCost = stock * 10; // 在庫費は売った後に計算．1個5円
		this.materialCost = delivery * Factory.price; // 材料費．冷凍コロッケの購入費は1個60円
		this.profit = earnings - (materialCost + inventoryCost); // 利益は収入から，材料費，在庫費を引いたもの．
	}
}
