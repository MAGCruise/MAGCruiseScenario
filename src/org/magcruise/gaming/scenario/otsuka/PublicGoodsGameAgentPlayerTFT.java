package org.magcruise.gaming.scenario.otsuka;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class PublicGoodsGameAgentPlayerTFT extends PublicGoodsGameAgentPlayer {

	double prepredistribution = 0; // 前々回の分配金
	double predictedValue = 0; // 予想金額

	public PublicGoodsGameAgentPlayerTFT(
			DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	@Override
	public void decide(PublicGoodsGameContext ctx) {
		if (ctx.roundnum == 1) {
			this.investment = this.account;
			predictedValue = (double) this.investment * ctx.plnum * 0.5;
		} else if (ctx.roundnum == 2) {
			if (predictedValue <= ctx.predistribution) {
				this.investment = this.account;
			} else {
				int ran = (int) (Math.random() * 4);
				if (ran == 0) {
					this.investment = 0;
				} else {
					this.investment = this.account;
				}
			}
		} else {
			predictedValue = prepredistribution * ctx.plnum * 0.5;
			// log.debug("ラウンド :
			// "+ctx.roundnum+"予想の分配金はああああああああああああああああああああああああああああああああああああああああ"+predictedValue);
			// log.debug("前回の分配金はああああああああああああああああああああああああああああああああああああああああ"+ctx.predistribution);
			// log.debug("前々回の分配金はああああああああああああああああああああああああああああああああああああああああ"+prepredistribution);
			if (predictedValue <= ctx.predistribution) {
				this.investment = this.account;
			} else {
				int ran = (int) (Math.random() * 4);
				if (ran == 0) {
					this.investment = 0;
				} else {
					this.investment = this.account;
				}
			}
			prepredistribution = ctx.predistribution;
		}
	}
}
