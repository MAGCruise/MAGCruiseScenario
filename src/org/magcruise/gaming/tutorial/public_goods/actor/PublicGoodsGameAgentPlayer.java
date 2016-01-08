package org.magcruise.gaming.tutorial.public_goods.actor;

import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.Player;

public abstract class PublicGoodsGameAgentPlayer extends Player {

	@HistoricalField(name = "口座")
	public int account = 1000;

	@HistoricalField(name = "出資金額")
	public int investment = 0;

	public PublicGoodsGameAgentPlayer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void init(PublicGoodsGameContext ctx) {
		log.debug("初期化！！！！！！！！！！！！");
		log.debug(name + ":" + account);

	}

	public abstract void decide(PublicGoodsGameContext ctx);

	public void pay(PublicGoodsGameContext ctx) {
		log.debug(this.name + ": お金出す！！！！");
		ctx.funds += investment;
		account -= investment;
	}

}
