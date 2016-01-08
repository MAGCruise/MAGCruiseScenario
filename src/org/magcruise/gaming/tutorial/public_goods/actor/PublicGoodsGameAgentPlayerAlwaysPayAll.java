package org.magcruise.gaming.tutorial.public_goods.actor;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class PublicGoodsGameAgentPlayerAlwaysPayAll
		extends PublicGoodsGameAgentPlayer {

	public PublicGoodsGameAgentPlayerAlwaysPayAll(
			DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	@Override
	public void decide(PublicGoodsGameContext ctx) {
		this.investment = this.account;

	}

}
