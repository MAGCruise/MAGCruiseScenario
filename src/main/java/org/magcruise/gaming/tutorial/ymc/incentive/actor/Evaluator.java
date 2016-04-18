package org.magcruise.gaming.tutorial.ymc.incentive.actor;

import java.text.DecimalFormat;

import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class Evaluator extends Player {

	public volatile String averageScore = "0.00";

	public Evaluator(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void setAverageScore(String scoreA, String scoreB, String scoreC) {
		this.averageScore = new DecimalFormat("0.00")
				.format((Double.valueOf(scoreA) + Double.valueOf(scoreB)
						+ Double.valueOf(scoreC)) / 3.0);
	}
}
