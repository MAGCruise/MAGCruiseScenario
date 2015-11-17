package org.magcruise.gaming.scenario.ultimatum;

import org.magcruise.gaming.model.game.Attribute;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.DefaultPlayerParameter;

public class UltPlayer extends Player {

	@Attribute(name = "手に入れたお金の<ruby><rb>合計</rb><rp>(</rp><rt>ごうけい</rt><rp>)</rp>")
	public int account;

	@Attribute(name = "おおぐま君が伝えた<ruby><rb>金額</rb><rp>(</rp><rt>きんがく</rt><rp>)</rp>")
	public int proposition;

	@Attribute(name = "こぐま君の<ruby><rb>返事</rb><rp>(</rp><rt>へんじ</rt><rp>)</rp>")
	public String yesOrNo;

	@Attribute(name = "<ruby><rb>今回</rb><rp>(</rp><rt>こんかい</rt><rp>)</rp>手に入れたお金")
	public int acquisition;

	public UltPlayer(DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}
}
