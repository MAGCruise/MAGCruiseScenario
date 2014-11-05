package org.magcruise.gaming.scenario.ele;

import java.util.List;

import org.apache.log4j.Logger;
import org.magcruise.gaming.model.Context;

public class Market {
	private Logger log = Logger.getLogger(this.getClass().getName());

	public void init(Context ctx) {
		List<CompanySetting> settings = CompanySettings.readSettings(ctx);
		for (CompanySetting setting : settings) {
			CompanyPlayer p = (CompanyPlayer) ctx.players.get(setting.name);
			p.init(setting);
		}
	}

	public void auction(Context ctx) {
		Companies unsatisfieds = new Companies(ctx);
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
