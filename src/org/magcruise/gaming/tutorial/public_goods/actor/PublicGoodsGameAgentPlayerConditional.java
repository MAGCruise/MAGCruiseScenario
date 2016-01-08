package org.magcruise.gaming.tutorial.public_goods.actor;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;

import gnu.mapping.SimpleSymbol;

public class PublicGoodsGameAgentPlayerConditional
		extends PublicGoodsGameAgentPlayer {

	public PublicGoodsGameAgentPlayerConditional(
			DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	@Override
	public void decide(PublicGoodsGameContext ctx) {
		if (ctx.roundnum == 0) {
			this.investment = this.account;
		} else {
			// log.debug("ひとつ前の投資額は" + history.prev(1,new
			// SimpleSymbol("investment")));
			int preinvest = (int) getLastValue(new SimpleSymbol("investment"));
			if (preinvest >= ctx.predistribution) {
				this.investment = this.account;
			} else {
				this.investment = 0;
			}
		}
	}
}
