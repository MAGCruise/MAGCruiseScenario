package org.magcruise.gaming.tutorial.scenario.public_goods;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;

import gnu.mapping.SimpleSymbol;

public class PublicGoodsGameAgentPlayerRandom
		extends PublicGoodsGameAgentPlayer {

	public PublicGoodsGameAgentPlayerRandom(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	@Override
	public void decide(PublicGoodsGameContext ctx) {
		if (ctx.roundnum == 0) {
			this.investment = (int) (this.account * Math.random());
		} else {
			int preinvest = (int) getLastValue(new SimpleSymbol("investment"));
			if (preinvest <= ctx.predistribution) {
				this.investment = (int) (this.account * Math.random());
			} else {
				this.investment = 0;
			}
		}
	}
}
