package org.magcruise.gaming.examples.ultimatum.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.examples.ultimatum.msg.FinalNote;
import org.magcruise.gaming.lang.SConstructor;
import org.magcruise.gaming.model.game.ActorName;
import org.magcruise.gaming.model.game.Player;
import org.magcruise.gaming.model.game.PlayerParameter;

public class FirstPlayer extends UltimatumPlayer {

	public List<Integer> defaultPropositions;

	public FirstPlayer(PlayerParameter playerParameter,
			List<Integer> propositions) {
		super(playerParameter);
		this.defaultPropositions = new ArrayList<>(propositions);
	}

	@Override
	public SConstructor<? extends Player> toConstructor(
			ToExpressionStyle style) {
		return SConstructor.toConstructor(style, getClass(),
				getPlayerParameter(), defaultPropositions);
	}

	public void note(UltimatumGameContext ctx) {
		syncRequestToInput(ctx, ctx.createForm("note-form", ctx, this),
				(params) -> {
					this.proposition = params.getArgAsInt(0);
					sendMessage(new FinalNote(name, ActorName.of("SmallBear"),
							this.proposition));
				});
	}

}
