package org.magcruise.gaming.examples.ele.resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.orangesignal.csv.annotation.CsvColumn;
import com.orangesignal.csv.annotation.CsvEntity;

@CsvEntity
public class CompanySetting {

	@CsvColumn(name = "name")
	public String name;

	@CsvColumn(name = "area")
	public String area;

	@CsvColumn(name = "type")
	public String type;

	@CsvColumn(name = "demand")
	public int demand;

	@CsvColumn(name = "reservation")
	public int reservation;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}