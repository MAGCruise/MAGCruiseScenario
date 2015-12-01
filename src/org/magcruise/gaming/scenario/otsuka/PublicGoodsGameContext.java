package org.magcruise.gaming.scenario.otsuka;

import java.util.ArrayList;
import java.util.Collections;

import org.magcruise.gaming.model.game.MainProperty;
import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.DefaultContextParameter;
import org.magcruise.gaming.model.game.Player;

public class PublicGoodsGameContext extends Context {

	@MainProperty(name = "ファンド総額")
	public int funds = 0;
	@MainProperty(name = "前回の分配金")
	public int predistribution = 0;

	public ArrayList<Integer> array = new ArrayList<Integer>();
	public int[] rank = new int[100];
	public int plnum = players.size();
	int i = 0;

	public PublicGoodsGameContext(DefaultContextParameter contextParameter) {
		super(contextParameter);
	}

	// ランキングの作成
	public String ranking() {
		int n = 0;
		String msg = "<br>";
		for (Player player : players) {
			PublicGoodsGameAgentPlayer agentPlayer = (PublicGoodsGameAgentPlayer) player;
			log.debug(agentPlayer.name + ":::::::::::::::::::::::::::::"
					+ agentPlayer.history);
			// log.debug("rank"+agentPlayer.name + ":" + agentPlayer.account);
			array.add(new Integer(agentPlayer.account));
		}

		Collections.sort(array);
		Collections.reverse(array);
		for (i = 0; i < players.size(); i++) {
			// log.debug(array.get(i));
			// log.debug("rank"+i+" : "+array.get(i));
			n = i + 1;
			msg += "rank" + n + " : " + array.get(i) + "<br>";
		}
		// log.debug(msg);
		array.clear();
		// log.debug(array.size());
		return msg;
	}

	// 投資額の精算、分配金の決定
	public void clearing() {
		for (Player player : players) {
			PublicGoodsGameAgentPlayer agentPlayer = (PublicGoodsGameAgentPlayer) player;
			log.debug(agentPlayer.name + ":" + agentPlayer.account);
		}

		double distribution = funds * 2 / players.size();
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
