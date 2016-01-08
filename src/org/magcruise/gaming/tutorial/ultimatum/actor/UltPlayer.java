package org.magcruise.gaming.tutorial.ultimatum.actor;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.model.game.HistoricalField;
import org.magcruise.gaming.model.game.Player;

public class UltPlayer extends Player {

	@HistoricalField(name = "手に入れたお金の<ruby><rb>合計</rb><rp>(</rp><rt>ごうけい</rt><rp>)</rp>")
	public int account;

	@HistoricalField(name = "おおぐま君が伝えた<ruby><rb>金額</rb><rp>(</rp><rt>きんがく</rt><rp>)</rp>")
	public int proposition;

	@HistoricalField(name = "こぐま君の<ruby><rb>返事</rb><rp>(</rp><rt>へんじ</rt><rp>)</rp>")
	public String yesOrNo;

	@HistoricalField(name = "<ruby><rb>今回</rb><rp>(</rp><rt>こんかい</rt><rp>)</rp>手に入れたお金")
	public int acquisition;

	public UltPlayer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
		account = 0;
	}

	public void status(UltContext ctx) {
		showMessage(tabulateHistory());
	}

	public void paid(int money) {
		this.acquisition = money;
		this.account += acquisition;

	}
}
