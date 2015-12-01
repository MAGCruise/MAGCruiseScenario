package org.magcruise.gaming.scenario.otsuka;

import org.magcruise.gaming.model.game.MainProperty;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.Player;

public abstract class PublicGoodsGameAgentPlayer extends Player {

	@MainProperty(name = "口座")
	public int account = 1000;

	@MainProperty(name = "出資金額")
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
