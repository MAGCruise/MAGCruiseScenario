package org.magcruise.gaming.examples.ultimatum;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class CroquetteGameSetting {

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
