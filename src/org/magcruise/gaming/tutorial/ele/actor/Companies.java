package org.magcruise.gaming.tutorial.ele.actor;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.model.game.Context;
import org.magcruise.gaming.model.game.Player;

public class Companies {
	private List<CompanyPlayer> companies = new ArrayList<>();

	public Companies(Context ctx) {
		for (Player p : ctx.players) {
			companies.add((CompanyPlayer) p);
		}
		align();
	}

	public void align() {
		List<CompanyPlayer> tmp = new ArrayList<CompanyPlayer>();
		for (CompanyPlayer p : companies) {
			if (p.remaindDemand == 0) {
				tmp.add(p);
			}
		}
		companies.removeAll(tmp);
	}

	public List<CompanyPlayer> getSellers() {
		List<CompanyPlayer> r = new ArrayList<>();
		for (CompanyPlayer p : companies) {
			if (p.type.equals("seller")) {
				r.add(p);
			}
		}
		return r;
	}

	public List<CompanyPlayer> getBuyers() {
		List<CompanyPlayer> r = new ArrayList<>();

		for (CompanyPlayer p : companies) {
			if (p.type.equals("buyer")) {
				r.add(p);
			}
		}
		return r;
	}

	public void remove(CompanyPlayer p) {
		this.companies.remove(p);

	}

	public List<CompanyPlayer> getBuyersIn(String area) {

		List<CompanyPlayer> r = new ArrayList<>();

		for (CompanyPlayer p : getBuyers()) {
			if (p.area.equals(area)) {
				r.add(p);
			}
		}
		return r;
	}

	public CompanyPlayer getLowestSeller() {
		int min = Integer.MAX_VALUE;
		CompanyPlayer lowestSeller = null;
		for (CompanyPlayer p : getSellers()) {
			if (p.inputPrice < min) {
				min = p.inputPrice;
				lowestSeller = p;
			}
		}
		return lowestSeller;
	}

	public CompanyPlayer getHiestBuyersIn(List<String> areas) {
		int max = Integer.MIN_VALUE;
		CompanyPlayer heiestBuyer = null;

		for (String area : areas) {
			for (CompanyPlayer p : getBuyersIn(area)) {
				if (p.inputPrice > max) {
					max = p.inputPrice;
					heiestBuyer = p;
				}
			}
		}
		return heiestBuyer;
	}

}
