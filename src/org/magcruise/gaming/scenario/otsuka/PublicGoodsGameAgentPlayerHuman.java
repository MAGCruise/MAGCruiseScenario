package org.magcruise.gaming.scenario.otsuka;

import org.magcruise.gaming.model.game.DefaultPlayerParameter;
import org.magcruise.gaming.ui.model.FormBuilder;
import org.magcruise.gaming.ui.model.attr.Max;
import org.magcruise.gaming.ui.model.attr.Min;
import org.magcruise.gaming.ui.model.attr.Required;
import org.magcruise.gaming.ui.model.input.NumberInput;

public class PublicGoodsGameAgentPlayerHuman
		extends PublicGoodsGameAgentPlayer {

	public PublicGoodsGameAgentPlayerHuman(
			DefaultPlayerParameter playerParameter) {
		super(playerParameter);
	}

	@Override
	public void decide(PublicGoodsGameContext ctx) {
		FormBuilder formBuilder = new FormBuilder(
				"ラウンド" + ctx.getRoundnum() + ": お金出しますか？");

		formBuilder.setInput(new NumberInput("共同基金への出資金額", "money", 1000,
				new Min(0), new Max(account), new Required()));

		syncRequestForInput(formBuilder.build(), params -> {
			this.investment = params.getInt("money");
			showMessage("あなたの出資金額は" + investment + "円です");
		});

		// try {
		// SExpressionUtils.applyProcedure("human:decide", ctx, this);
		// } catch (ApplyException e) {
		// log.error(e);
		// }
	}
}
