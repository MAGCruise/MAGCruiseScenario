package org.magcruise.gaming.examples.ymc.incentive.actor;

import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.Player;

public class JPExpert extends Player {
	private final String[] answers = {
			"菌には、いろいろな種類があります。菌は、カビであり、簡単に増殖するので、適切な防除をしないと被害が大きくなります。",
			"乾田直播は、乾いた土に直に播種することです。播種後に、田んぼに水を入れます。これは別に、田んぼに水を入れておいて、水の上から播種したり、湿った状態の土に播種する方法もあります。" };

	public String answer = answers[0];

	public JPExpert(PlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void init(int roundnum) {
		answer = answers[roundnum];
	}
}
