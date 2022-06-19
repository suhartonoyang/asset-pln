package com.project.assetpln.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
	private String code;
	private String message;
	private Integer page;
	private Integer pageSize;
	private Integer totalPages;
	private Integer totalAllData;
	private List<?> data;
}
