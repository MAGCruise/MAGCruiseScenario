package org.magcruise.gaming.scenario.otsuka;

import org.magcruise.gaming.model.game.Attribute;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public abstract class PublicGoodsGameAgentPlayer extends Player {
	@Attribute(name = "口座")
	public int account = 1000;

	@Attribute(name = "出資金額")
	public int investment = 0;

	public PublicGoodsGameAgentPlayer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void init(PublicGoodsGameContext ctx) {
		log.debug("初期化！！！！！！！！！！！！");
		log.debug(name + ":" + account);

	}

	public abstract void decide(PublicGoodsGameContext ctx);

	public void status(PublicGoodsGameContext ctx) {
		showMessage(tabulateHistory());
		showMessage(ctx.predistribution + "円を受けとりました");
	}

	public void pay(PublicGoodsGameContext ctx) {
		log.debug(this.name + ": お金出す！！！！");
		ctx.funds += investment;
		account -= investment;
	}
}
