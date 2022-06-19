package com.project.assetpln.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Data;

@Data
@Getter
@Setter
@ToString
public class SummaryFloodingPerYear {
	private Integer year;
	private Integer totalGarduSubmerged;
	private Integer totalNeighbourhoodSubmerged;
}
