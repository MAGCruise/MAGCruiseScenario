package org.magcruise.gaming.scenario.ultimatum;

import org.magcruise.gaming.model.game.MainProperty;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class UltPlayer extends Player {

	@MainProperty(name = "手に入れたお金の<ruby><rb>合計</rb><rp>(</rp><rt>ごうけい</rt><rp>)</rp>")
	public int account;

	@MainProperty(name = "おおぐま君が伝えた<ruby><rb>金額</rb><rp>(</rp><rt>きんがく</rt><rp>)</rp>")
	public int proposition;

	@MainProperty(name = "こぐま君の<ruby><rb>返事</rb><rp>(</rp><rt>へんじ</rt><rp>)</rp>")
	public String yesOrNo;

	@MainProperty(name = "<ruby><rb>今回</rb><rp>(</rp><rt>こんかい</rt><rp>)</rp>手に入れたお金")
	public int acquisition;

	public UltPlayer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}
}
