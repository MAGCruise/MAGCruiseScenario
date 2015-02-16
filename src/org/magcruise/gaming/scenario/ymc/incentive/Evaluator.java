package org.magcruise.gaming.scenario.ymc.incentive;

import gnu.mapping.Symbol;

import java.text.DecimalFormat;

import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.MessageBox;
import org.magcruise.gaming.model.Player;
import org.magcruise.gaming.model.Properties;

public class Evaluator extends Player {

	public String averageScore = "0.00";

	public Evaluator(Symbol playerName, Symbol playerType) {
		super(playerName, playerType);
	}

	public Evaluator(Symbol playerName, Symbol playerType, String operatorId,
			Properties props, MessageBox msgbox, History history) {
		super(playerName, playerType, operatorId, props, msgbox, history);
	}

	public void setAverageScore(String scoreA, String scoreB, String scoreC) {
		this.averageScore = new DecimalFormat("0.00").format((Double
				.valueOf(scoreA) + Double.valueOf(scoreB) + Double
				.valueOf(scoreC)) / 3.0);
	}
}
