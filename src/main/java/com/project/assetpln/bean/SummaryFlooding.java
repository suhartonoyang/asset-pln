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
public class SummaryFlooding {
	private List<SummaryFloodingPerLocation> locations;
}
