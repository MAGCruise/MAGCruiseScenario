package org.magcruise.gaming.scenario.croquette.player;

import gnu.mapping.Symbol;

import java.util.Arrays;
import java.util.List;

import org.magcruise.gaming.model.Player;

public class Shop extends Player {

	public int order;
	public int stock;
	public List<Integer> orders;
	public int delivery;
	public int price;
	public int inventoryCost;
	public int sales;
	public int materialCost;
	public int earnings;
	public int profit;
	public int demand;

	public Shop(Symbol playerName, Symbol playerType) {
		super(playerName, playerType);
	}

	public void init() {
		this.orders = Arrays.asList(200, 200, 200, 200, 200, 200, 200, 200,
				200, 200);

		this.stock = 100;
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

	public void closing(Shop other, int demand) {
		int totalPrice = price + other.price;
		this.demand = (int) Math.floor(demand
				* ((totalPrice - price) / totalPrice)); // 売れる量は値段の逆比

		if (price < other.price) {
			this.demand += 100;// 安い金額を付けた方にボーナス
		}

		this.sales = (stock >= demand) ? demand : stock; // 需要だけ売れる．上限は在庫量．

		this.stock -= sales;
		this.earnings = sales * price; // 収入は売った個数*単価
		this.inventoryCost = stock * 5; // 在庫費は売った後に計算．1個5円
		this.materialCost = delivery * 60; // 材料費．冷凍コロッケの購入費は1個60円
		this.profit = earnings - (materialCost + inventoryCost); // 利益は収入から，材料費，在庫費を引いたもの．
	}
}
