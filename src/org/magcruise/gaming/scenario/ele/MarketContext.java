package org.magcruise.gaming.scenario.ele;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.Players;
import org.magcruise.gaming.model.Properties;

public class MarketContext extends Context {
	private static Logger log = LogManager.getLogger();

	public MarketContext(Properties props, Players players, String scenarioHome) {
		super(props, players, scenarioHome);
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
		CompanyPlayer buyer = unsatisfieds.getHiestBuyersIn(constraints
				.getTransferableAreasFrom(seller.area));

		if (buyer == null) {
			unsatisfieds.remove(seller);
			pair(unsatisfieds, constraints);
		} else {

			String from = seller.area;
			String to = buyer.area;
			int capacity = constraints.getCapacity(from, to);
			int amount = seller.demand <= buyer.demand ? seller.demand
					: buyer.demand;
			amount = capacity <= amount ? capacity : amount;
			constraints.transfer(from, to, amount);

			int price = (seller.reservation + buyer.reservation) / 2;
			seller.bond(buyer, price, amount);
			buyer.bond(seller, price, amount);

			log.debug(seller.name + "-" + buyer.name);
			pair(unsatisfieds, constraints);
		}

	}

}
