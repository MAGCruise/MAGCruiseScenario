package org.magcruise.gaming.scenario.otsuka;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;
import org.magcruise.gaming.model.game.Player;

public class PublicGoodsGameContext extends Context {

	public PublicGoodsGameContext(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	public int funds = 0;
	public int predistribution = 0;

	public void clearing() {
		for (Player player : players) {
			PublicGoodsGameAgentPlayer agentPlayer = (PublicGoodsGameAgentPlayer) player;
			log.debug(agentPlayer.name + ":" + agentPlayer.account);
		}

		double distribution = funds * 0.5;// 2 / players.size();
		predistribution = (int) distribution;
		log.debug("分配金額は" + distribution);
		funds = 0;
		for (Player player : players) {
			PublicGoodsGameAgentPlayer agentPlayer = (PublicGoodsGameAgentPlayer) player;
			agentPlayer.account += distribution;
			log.debug(agentPlayer.name + ":" + agentPlayer.account);
		}

	}

}
