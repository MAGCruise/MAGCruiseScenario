package org.magcruise.gaming.scenario.otsuka;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;

import gnu.mapping.SimpleSymbol;

public class PublicGoodsGameAgentPlayerTFT extends PublicGoodsGameAgentPlayer {

	public PublicGoodsGameAgentPlayerTFT(DefaultPlayerParameter playerParameter) {
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
			// log.debug("ひとつ前の投資額は" + preinvest);
			if (preinvest <= ctx.predistribution) {
				this.investment = this.account;
			} else {
				this.investment = 0;
			}
		}
	}
}
