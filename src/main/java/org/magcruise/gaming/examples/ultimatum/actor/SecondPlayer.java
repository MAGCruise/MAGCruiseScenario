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
import org.magcruise.gaming.ui.model.attr.Required;
import org.magcruise.gaming.ui.model.input.Input;
import org.magcruise.gaming.ui.model.input.RadioInput;
import org.nkjmlab.util.html.Tags;

public class SecondPlayer extends UltimatumPlayer {

	public List<Boolean> defaultYesOrNos;

	public SecondPlayer(PlayerParameter playerParameter, List<Boolean> yesOrNos) {
		super(playerParameter);
		this.defaultYesOrNos = new ArrayList<>(yesOrNos);
	}

	@Override
	public SConstructor<? extends Player> toConstructor(ToExpressionStyle style) {
		return SConstructor.toConstructor(style, getClass(), getPlayerParameter(), defaultYesOrNos);
	}

	public void beforeNegotiation(UltimatumGameContext ctx) {
		showAlertMessage(Alert.INFO, "相手からの通牒を待っています．");
	}

	public String getDefaultYesOrNo(int roundnum) {
		if (defaultYesOrNos.get(roundnum)) {
			return "yes";
		}
		return "no";
	}

	public void judge(UltimatumGameContext ctx) {
		FinalNote msg = takeMessage(FinalNote.class);
		Element label = Tags.append(Tags.create("p"), Tags.ruby("第", "だい"), "ラウンドです",
				"こぐま君，<br>おおぐま君は", UltimatumGameContext.providedVal, "円を",
				Tags.ruby("受", "う"), "けとり，あなたに", msg.proposition, "円を", Tags.ruby("分", "わ"),
				"けると言いました．", "受けとりますか？",
				Tags.create("div").attr("class", "pull-right").append(Tags.create("img")
						.attr("src", "https://i.gyazo.com/d4cf1336d68f315fc9a88ba446f69488.jpg")
						.toString()));
		Input input = new RadioInput(Tags.ruby("受", "う").append("けとる？").toString(), "yes-or-no",
				getDefaultYesOrNo(ctx.getRoundnum()), new String[] { "yes", "no" },
				new String[] { "yes", "no" }, new Required());

		syncRequestToInput(new Form(label.toString(), input),
				params -> {
					this.yesOrNo = params.getArgAsString(0);
					showAlertMessage(Alert.INFO, this.yesOrNo + "と答えました．");
				}, e -> {
					showAlertMessage(Alert.DANGER, e.getMessage());
					judge(ctx);
				});
	}
}
