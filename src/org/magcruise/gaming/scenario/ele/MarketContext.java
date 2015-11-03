package org.magcruise.gaming.scenario.ele;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.magcruise.gaming.lang.Properties;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.History;
import org.magcruise.gaming.model.Players;

public class MarketContext extends Context {

	public MarketContext(int roundnum, Properties props, History history,
			Players players, String scenarioHome) {
		super(roundnum, props, history, players, scenarioHome);
	}

	public void init() {
		List<CompanySetting> settings = CompanySettings.readSettings(this);
		for (CompanySetting setting : settings) {
			CompanyPlayer player = (CompanyPlayer) players.get(setting.name);
			player.init(setting);
		}
	}

	public void auction() {
		Companies unsatisfieds = new Companies(this);
		Constraints constraints = new Constraints();
		pair(unsatisfieds, constraints);
	}

	private void pair(Companies unsatisfieds, Constraints constraints) {
		unsatisfieds.align();
		CompanyPlayer seller = unsatisfieds.getLowestSeller();

		if (seller == null) {
			return;
		}
		CompanyPlayer buyer = unsatisfieds.getHiestBuyersIn(
				constraints.getTransferableAreasFrom(seller.area));

		if (buyer == null) {
			unsatisfieds.remove(seller);
			pair(unsatisfieds, constraints);
		} else {

			String from = seller.area;
			String to = buyer.area;
			int capacity = constraints.getCapacity(from, to);
			int amount = seller.remaindDemand <= buyer.remaindDemand
					? seller.remaindDemand : buyer.remaindDemand;
			amount = capacity <= amount ? capacity : amount;
			constraints.transfer(from, to, amount);

			int price = (seller.inputPrice + buyer.inputPrice) / 2;
			seller.bond(buyer, price, amount);
			buyer.bond(seller, price, amount);

			log.debug(seller.name + "-" + buyer.name);
			pair(unsatisfieds, constraints);
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
