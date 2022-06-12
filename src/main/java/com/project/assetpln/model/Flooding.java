// Generated with g9.

package com.project.assetpln.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "flooding")
@Entity
public class Flooding implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Integer id;
	@Column(nullable = false, length = 255)
	private String penyulang;
	@Column(name = "gardu_no", nullable = false, length = 255)
	private String garduNo;
	@Column(name = "disaster_date", nullable = false)
	private Date disasterDate;
	@Column(name = "is_gardu_submerged", nullable = false, precision = 10)
	private int isGarduSubmerged;
	@Column(name = "is_neighbourhood_submerged", nullable = false, precision = 10)
	private int isNeighbourhoodSubmerged;
	@Column(name = "created_by", nullable = false, length = 255)
	private String createdBy;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "gardu_induk_id", nullable = false)
	@JsonIgnoreProperties({ "flooding", "mappingLocationGarduInduk" })
	private GarduInduk garduInduk;

	@ManyToOne(optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	@JsonIgnoreProperties({ "flooding", "mappingLocationGarduInduk" })
	private Location location;
	
	@Transient
	private String isGarduSubmergedDescription;
	
	public String getIsGarduSubmergedDescription() {
		switch (isGarduSubmerged) {
		case 1:
			return "YES";
		default:
			return "NO";
		}
	}
	
	@Transient
	private String isNeighbourhoodSubmergedDescription;
	
	public String getIsNeighbourhoodSubmergedDescription() {
		switch (isNeighbourhoodSubmerged) {
		case 1:
			return "YES";
		default:
			return "NO";
		}
	}
	
}
