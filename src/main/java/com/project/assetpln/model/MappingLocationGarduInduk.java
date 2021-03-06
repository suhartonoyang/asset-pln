// Generated with g9.

package com.project.assetpln.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "mapping_location_gardu_induk")
@Entity
public class MappingLocationGarduInduk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(name = "created_by", nullable = false, length = 255)
	private String createdBy;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	@JsonIgnoreProperties("mappingLocationGarduInduk")
	private Location location;

	@ManyToOne(optional = false)
	@JoinColumn(name = "gardu_induk_id", nullable = false)
	@JsonIgnoreProperties({ "mappingLocationGarduInduk", "flooding" })
	private GarduInduk garduInduk;

}
