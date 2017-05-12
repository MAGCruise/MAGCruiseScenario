package org.magcruise.gaming.examples.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.magcruise.gaming.examples.ultimatum.msg.FinalNote;
import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;
import org.magcruise.gaming.model.game.message.Alert;
import org.magcruise.gaming.ui.model.Form;
import org.magcruise.gaming.ui.model.attr.Max;
import org.magcruise.gaming.ui.model.attr.Min;
import org.magcruise.gaming.ui.model.attr.Required;
import org.magcruise.gaming.ui.model.input.NumberInput;
import org.nkjmlab.util.html.Tags;

public class FirstPlayer extends UltimatumPlayer {

	public List<Integer> defaultPropositions;

	public FirstPlayer(PlayerParameter playerParameter,
			List<Integer> propositions) {
		super(playerParameter);
		this.defaultPropositions = new ArrayList<>(propositions);
	}

	@Override
	public SConstructor<? extends Player> toConstructor(ToExpressionStyle style) {
		return SConstructor.toConstructor(style, getClass(), getPlayerParameter(),
				defaultPropositions);
	}

	public void note(UltimatumGameContext ctx) {
		Element label = Tags.append(Tags.create("p"), Tags.ruby("第", "だい"), "ラウンドです",
				"おおぐま君，<br>あなたは", UltimatumGameContext.providedVal, "円を",
				Tags.ruby("受", "う"), "けとりました．こぐま君にいくらを", Tags.ruby("分", "わ"),
				"けますか？",
				Tags.create("div").attr("class", "pull-right").append(Tags.create("img")
						.attr("src", "https://i.gyazo.com/eddcf71e96234685cbaa412c463b9c7a.jpg")
						.toString()));
		NumberInput input = new NumberInput(Tags.ruby("金額", "きんがく").toString(), "proposition",
				defaultPropositions.get(ctx.getRoundnum()), new Min(0),
				new Max(UltimatumGameContext.providedVal),
				new Required());

		syncRequestToInput(new Form(label.toString(), input), params -> {
			this.proposition = params.getArgAsInt(0);
			sendMessage(new FinalNote(name, UltimatumGameContext.SECOND_PLAYER, this.proposition));
			showAlertMessage(Alert.INFO, this.proposition + "円を分けると伝えました．" + "返事を待っています．");
		}, e -> {
			showAlertMessage(Alert.DANGER, e.getMessage());
			note(ctx);
		});
	}

	private void build() {
		// TODO 自動生成されたメソッド・スタブ

	}

}
