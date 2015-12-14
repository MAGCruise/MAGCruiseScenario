package org.magcruise.gaming.tutorial.scenario.otsuka;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.Player;

import gnu.mapping.SimpleSymbol;

public class PublicGoodsGameAgentPlayerAlwaysNo
		extends PublicGoodsGameAgentPlayer {

	public PublicGoodsGameAgentPlayerAlwaysNo(
			DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	@Override
	public void decide(PublicGoodsGameContext ctx) {
		this.investment = 0;

		for (Player player : ctx.players) {
			PublicGoodsGameAgentPlayer agentPlayer = (PublicGoodsGameAgentPlayer) player;

			if (ctx.roundnum != 0) {
				log.debug("-----------------" + agentPlayer
						.getLastValue(new SimpleSymbol("investment")));
			}
		}

	}
}
