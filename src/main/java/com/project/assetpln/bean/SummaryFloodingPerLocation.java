package com.project.assetpln.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import lombok.Data;

@Data
@Getter
@Setter
@ToString
public class SummaryFloodingPerLocation {
	private Integer no;
	private Integer locationId;
	private String locationName;
	private List<SummaryFloodingPerYear> years;

	public Integer getGrandTotalGarduSubmerged() {
		if (years == null || years.isEmpty()) {
			return 0;
		}

		return years.stream().mapToInt(m -> m.getTotalGarduSubmerged()).sum();
	}

	public Integer getGrandTotalNeighbourhoodSubmerged() {
		if (years == null || years.isEmpty()) {
			return 0;
		}

		return years.stream().mapToInt(m -> m.getTotalNeighbourhoodSubmerged()).sum();
	}

}
