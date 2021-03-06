// Generated with g9.

package com.project.assetpln.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "upload_history")
@Entity
public class UploadHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Integer id;
	@Column(name = "upload_date", nullable = false)
	private Date uploadDate;
	@Column(name = "created_by", nullable = false, length = 255)
	private String createdBy;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "file_id", nullable = false)
	@JsonIgnoreProperties("uploadHistory")
	private File file;

	@ManyToOne(optional = false)
	@JoinColumn(name = "location_id", nullable = false)
	@JsonIgnoreProperties({ "uploadHistory", "mappingLocationGarduInduk" })
	private Location location;

	@Transient
	private String uploadDateSimplifly;

	public String getUploadDateSimplifly() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(uploadDate);
	}

}
