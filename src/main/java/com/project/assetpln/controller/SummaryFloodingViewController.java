package com.project.assetpln.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.assetpln.bean.PaginationResponse;
import com.project.assetpln.bean.Response;
import com.project.assetpln.bean.SummaryFlooding;
import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.UploadHistory;
import com.project.assetpln.service.FloodingService;
import com.project.assetpln.service.SummaryFloodingViewService;

@RestController
@RequestMapping("${rest.prefix:}/summary-floodings")
public class SummaryFloodingViewController {

	@Autowired
	private SummaryFloodingViewService summaryFloodingViewService;

	@GetMapping
	public ResponseEntity<Response> getFloodingsByCriteria(@RequestParam(required = false) Integer locationId,
			@RequestParam(required = false) Integer year) {
		SummaryFlooding summaryFlooding = summaryFloodingViewService.getSummaryFloodingByCriteria(locationId, year);
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (summaryFlooding==null) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(Arrays.asList(summaryFlooding));

		return ResponseEntity.ok(response);
	}

}
