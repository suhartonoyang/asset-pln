package com.project.assetpln.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SummaryFloodingViewId implements Serializable{

	private Integer locationId;
	private Integer year;
}
