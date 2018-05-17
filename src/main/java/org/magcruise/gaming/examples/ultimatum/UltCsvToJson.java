package org.magcruise.gaming.examples.ultimatum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.Logger;
import org.magcruise.gaming.examples.ultimatum.resource.UltimatumGameResourceLoader;
import org.magcruise.gaming.manager.session.GameSessionSeed;
import org.magcruise.gaming.manager.session.GameSessionsSetting;
import org.magcruise.gaming.manager.session.ResourceLoader;
import org.nkjmlab.util.csv.CsvUtils;
import org.nkjmlab.util.json.JsonUtils;
import org.nkjmlab.util.log4j.LogManager;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class UltCsvToJson {
	protected static Logger log = LogManager.getLogger();

	private static Class<? extends ResourceLoader> loaderClazz = UltimatumGameResourceLoader.class;
	private static String csvFile = "ult2-2018.csv";
	private static String baseSettingFile = "base-settings.json";

	public static void main(String[] args) {
		GameSessionsSetting settings = JsonUtils.decode(
				loaderClazz.getResourceAsStream(baseSettingFile), GameSessionsSetting.class);
		List<GameSessionSeed> seeds = CsvUtils.readList(UltCsvToJson.class,
				loaderClazz.getResourceAsStream(csvFile)).stream()
				.map(line -> {
					GameSessionSeed s = new GameSessionSeed();
					s.setSessionName(line.getGroup());
					s.setUserIds(Arrays.asList(line.getFirstplayer(), line.getSecondplayer()));
					return s;
				}).collect(Collectors.toList());
		settings.setSeeds(seeds.toArray(new GameSessionSeed[0]));
		System.out.println(JsonUtils.encode(settings, true));
	}

	@CsvColumn(name = "group")
	private String group;
	@CsvColumn(name = "firstplayer")
	private String firstplayer;
	@CsvColumn(name = "secondplayer")
	private String secondplayer;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFirstplayer() {
		return firstplayer;
	}

	public void setFirstplayer(String firstplayer) {
		this.firstplayer = firstplayer;
	}

	public String getSecondplayer() {
		return secondplayer;
	}

	public void setSecondplayer(String secondplayer) {
		this.secondplayer = secondplayer;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
