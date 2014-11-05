package org.magcruise.gaming.scenario.ele;

import java.util.ArrayList;
import java.util.List;

import org.magcruise.gaming.model.Context;
import org.magcruise.gaming.model.Player;

public class Companies {
	List<CompanyPlayer> companies = new ArrayList<>();

	public void align() {
		List<CompanyPlayer> tmp = new ArrayList<CompanyPlayer>();
		for (CompanyPlayer p : companies) {
			if (p.demand == 0) {
				tmp.add(p);
			}
		}
		companies.removeAll(tmp);
	}

	public Companies(Context ctx) {
		for (Player p : ctx.players.values()) {
			CompanyPlayer cp = (CompanyPlayer) p;
			if (cp.demand == 0) {
				continue;
			}
			companies.add(cp);
		}
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
			if (p.reservation < min) {
				min = p.reservation;
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
				if (p.reservation > max) {
					max = p.reservation;
					heiestBuyer = p;
				}
			}
		}
		return heiestBuyer;
	}

}
