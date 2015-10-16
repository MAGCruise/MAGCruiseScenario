package org.magcruise.gaming.scenario.ymc.incentive;

import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;

import gnu.mapping.Symbol;

public class JPExpert extends Player {
	String[] answers = {
			"乾田直播は、乾いた土に直に播種することです。播種後に、田んぼに水を入れます。これは別に、田んぼに水を入れておいて、水の上から播種したり、湿った状態の土に播種する方法もあります。",
			"菌には、いろいろな種類があります。稲に被害を及ぼす菌としては、いもち病菌や紋枯病菌などがあります。菌は、カビであり、簡単に増殖するので、適切な防除をしないと被害が大きくなります。" };

	public String answer = answers[0];

	public JPExpert(Symbol playerName, Symbol playerType, String operatorId,
			Properties props, MessageBox msgbox, History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}
}
