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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "user")
@ToString
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private int id;
	@Column(unique = true, nullable = false, length = 255)
	private String username;
	@Column(nullable = false, length = 255)
	private String password;
	@Column(nullable = false, length = 255)
	private String name;
	@Column(nullable = false, length = 255)
	private String role;
	@Column(name = "password_key", nullable = false, length = 255)
	private String passwordKey;
	@Column(name = "created_by", nullable = false, length = 255)
	private String createdBy;
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "aes_configuration_id", nullable = false)
	@JsonIgnoreProperties(value = "user")
	private AesConfiguration aesConfiguration;
}
