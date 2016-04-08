package org.magcruise.gaming.tutorial.croquette.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.tutorial.croquette.msg.CroquetteDelivery;
import org.magcruise.gaming.tutorial.croquette.msg.CroquetteOrder;
import org.magcruise.gaming.tutorial.croquette.msg.PotatoDelivery;
import org.magcruise.gaming.tutorial.croquette.msg.PotatoOrder;
import org.magcruise.gaming.ui.model.Form;
import org.magcruise.gaming.util.SExpressionUtils;

import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;

public class CroquetteFactory extends Player {

	@HistoricalField(name = "価格")
	public static volatile int price = 60;

	@HistoricalField(name = "発注個数(じゃがいも)")
	public volatile int orderOfPotato;
	@HistoricalField(name = "納品個数(じゃがいも)")
	public volatile int deliveredPotato;
	@HistoricalField(name = "生産個数")
	public volatile int production;
	@HistoricalField(name = "在庫個数")
	public volatile int stock = 0;
	@HistoricalField(name = "加工費")
	public volatile int machiningCost;
	@HistoricalField(name = "材料費")
	public volatile int materialCost;
	@HistoricalField(name = "在庫費")
	public volatile int inventoryCost;
	@HistoricalField(name = "売上個数")
	public volatile int sales;
	@HistoricalField(name = "売上高")
	public volatile int earnings;
	@HistoricalField(name = "利益")
	public volatile int profit;
	@HistoricalField(name = "納品希望数")
	public volatile int demand;

	@HistoricalField(name = "受注内容")
	public volatile Map<Symbol, Number> orders = new ConcurrentHashMap<>();

	// @HistoricalField(name = "発注個数のデフォルト値")
	public List<Number> defaultOrdersToFarmer;

	public CroquetteFactory(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public CroquetteFactory(DefaultPlayerParameter playerParameter,
			List<Number> ordersToFarmer) {
		super(playerParameter);
		this.defaultOrdersToFarmer = ordersToFarmer;
	}

	@Override
	public SConstructor<? extends Player> toConstructor() {
		return SExpressionUtils.toConstructor(this.getClass(),
				toDefaultPlayerParameter(), defaultOrdersToFarmer);
	}

	public void init(Market ctx) {
		String msg = (String) callProcedure("factory:init-msg", ctx, this);
		syncRequestToInput(new Form(msg), (params) -> {
			return;
		});
		showMessage(msg);
	}

	public void refresh(Market ctx) {
		showMessage(createMessage("factory:refresh-msg", ctx, this));
		syncRequestToInput(createForm("end-day-form", ctx), (param) -> {
		});
		showMessage(createMessage("start-day-msg", ctx));
		refresh();
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
		this.orders = new ConcurrentHashMap<>();
	}

	public void order(Market ctx) {
		syncRequestToInput(createForm("factory:order-form", ctx, this),
				(param) -> {
					this.orderOfPotato = param.getArgAsInt(0);
					showMessage(createMessage("factory:after-order-msg", ctx,
							this));
					sendGameMessage(new PotatoOrder(name,
							Symbol.parse("Farmer"), this.orderOfPotato));
				});
	}

	public void receiveOrder(Market ctx) {
		takeAllMessages(CroquetteOrder.class).forEach((msg) -> {
			this.orders.put(msg.from, new Integer(msg.num));
		});
	}

	public int getTotalOrder(Context ctx) {
		if (ctx.roundnum < 2) {
			return 0;
		}

		@SuppressWarnings("unchecked")
		Map<Symbol, Number> prevOrders = (Map<Symbol, Number>) getValueBefore(
				new SimpleSymbol("orders"), 2);
		int tmp = 0;
		for (Number num : prevOrders.values()) {
			tmp += Integer.valueOf(num.toString());
		}
		return tmp;
	}

	public void delivery(Market ctx) {
		List<String> msgs = new ArrayList<>();
		int stockBeforeDelivery = this.stock;
		ctx.players.getPlayers(Shop.class).forEach((Shop p) -> {
			int d = delivery(ctx, p, stockBeforeDelivery);
			msgs.add(createMessage("factory:delivery-msg", p.name, d));
			sendGameMessage(new CroquetteDelivery(name, p.name, d));
		});
		showMessage(createMessage("factory:after-delivery-msg",
				String.join(",", msgs), this));
	}

	public int delivery(Context ctx, Shop shop, int stockBeforeDelivery) {

		int order = 0;
		if (ctx.roundnum < 2) {
			order = 0;
		} else {
			@SuppressWarnings("unchecked")
			Map<Symbol, Number> tmp = (Map<Symbol, Number>) getValueBefore(
					toSymbol("orders"), 2);

			order = tmp.get(shop.name).intValue();
		}
		int totalOrder = getTotalOrder(ctx);
		int delivery = totalOrder <= stockBeforeDelivery ? order
				: (int) Math.floor(
						stockBeforeDelivery * ((double) order / totalOrder));
		this.stock -= delivery;
		this.demand += order;
		this.sales += delivery;
		return delivery;
	}

	public void receiveDelivery(Market ctx) {
		takeAllMessages(PotatoDelivery.class).forEach((msg) -> {
			receiveDeliveryAndProduce(msg.num);
			showMessage(createMessage("factory:receive-delivery-msg", msg.num,
					production, stock));
		});
	}

	public void receiveDeliveryAndProduce(int potato) {
		this.deliveredPotato = potato;
		this.production = deliveredPotato * 2; // 1つのじゃがいもからコロッケが1つ
		this.stock += production;
	}

	public void closing(Market ctx) {
		this.inventoryCost = (stock - production) * 10; // 持ち越し在庫(=在庫量-生産量)*単価
		this.materialCost = deliveredPotato * 20; // 材料費(じゃがいも個数*単価)
		this.machiningCost = +production * 20; // 加工費(生産個数*単価)
		this.earnings = sales * price; // 収入は個数×単価
		this.profit = earnings - (materialCost + machiningCost + inventoryCost);// 利益は，収入から材料費，加工費，在庫維持費を引いたもの．
	}
}