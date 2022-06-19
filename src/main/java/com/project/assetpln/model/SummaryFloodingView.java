package com.project.assetpln.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.Subselect;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@IdClass(SummaryFloodingViewId.class)
@Entity
@Table(name = "summary_flooding_v")
public class SummaryFloodingView {

	@Id
	@Column(name = "location_id")
	private Integer locationId;
	@Id
	@Column(name = "year")
	private Integer year;
	
	@Column(name = "location_name")
	private String locationName;
	@Column(name = "total_gardu_submerged")
	private Integer totalGarduSubmerged;
	@Column(name = "total_neighbourhood_submerged")
	private Integer totalNeighbourhoodSubmerged;
}
