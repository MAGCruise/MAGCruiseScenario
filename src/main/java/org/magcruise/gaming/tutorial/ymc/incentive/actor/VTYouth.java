package org.magcruise.gaming.tutorial.ymc.incentive.actor;

import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class VTYouth extends Player {

	public volatile String[] questions = { "菌はお米に害を与えますか？", "乾田直播とは何ですか？" };
	public volatile String[] questionsEn = { "Do the bacteria harm rice?",
			"What is a dry paddy field direct seeding?" };

	public volatile String question = questions[0];

	public volatile String questionEn = questionsEn[0];

	volatile boolean[] thanksMessageTimings = { true, false };
	volatile boolean[] failureMessageTimings = { true, false };

	public VTYouth(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	public void init(int roundnum) {
		question = questions[roundnum];
		questionEn = questionsEn[roundnum];
	}
}
