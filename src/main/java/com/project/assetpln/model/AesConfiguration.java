// Generated with g9.

package com.project.assetpln.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "aes_configuration")
@Entity
public class AesConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(name = "encrypt_algo", nullable = false, length = 255)
	private String encryptAlgo;
	@Column(name = "tag_length_byte", nullable = false, precision = 10)
	private int tagLengthByte;
	@Column(name = "iv_length_byte", nullable = false, precision = 10)
	private int ivLengthByte;
	@Column(name = "salt_length_byte", precision = 10)
	private int saltLengthByte;
	@Column(name = "created_by", nullable = false, length = 255)
	private String createdBy;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@OneToMany(mappedBy = "aesConfiguration")
	@JsonIgnoreProperties("aesConfiguration")
	private Set<User> user;

}
