package org.magcruise.gaming.examples.croquette;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.examples.croquette.resource.CroquetteGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionSeed;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.magcruise.gaming.manager.session.ResourceLoader;
import org.nkjmlab.util.csv.CsvUtils;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class CroquetteCsvToJson {
	protected static Logger log = LogManager.getLogger();

	private static Class<? extends ResourceLoader> loaderClazz = CroquetteGameResourceLoader.class;
	private static String csvFile = "croquette-2018.csv";
	private static String baseSettingFile = "base-settings.json";

	public static void main(String[] args) {
		GameSessionsSetting settings = JsonUtils.decode(
				loaderClazz.getResourceAsStream(baseSettingFile),
				GameSessionsSetting.class);
		List<GameSessionSeed> seeds = CsvUtils.readList(CroquetteCsvToJson.class,
				loaderClazz.getResourceAsStream(csvFile)).stream()
				.map(line -> {
					GameSessionSeed s = new GameSessionSeed();
					s.setSessionName(line.getGroup());
					s.setUserIds(
							Arrays.asList(line.getFactory(), line.getShop1(), line.getShop2()));
					return s;
				}).collect(Collectors.toList());
		settings.setSeeds(seeds.toArray(new GameSessionSeed[0]));
		System.out.println(JsonUtils.encode(settings, true));
	}

	@CsvColumn(name = "Group")
	private String group;

	@CsvColumn(name = "Factory")
	private String factory;

	@CsvColumn(name = "Shop1")
	private String shop1;

	@CsvColumn(name = "Shop2")
	private String shop2;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String firstplayer) {
		this.factory = firstplayer;
	}

	public String getShop1() {
		return shop1;
	}

	public void setShop1(String secondplayer) {
		this.shop1 = secondplayer;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getShop2() {
		return shop2;
	}

	public void setShop2(String shop2) {
		this.shop2 = shop2;
	}

}
