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
import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.UploadHistory;
import com.project.assetpln.service.FloodingService;

@RestController
@RequestMapping("${rest.prefix:}/floodings")
public class FloodingController {

	@Autowired
	private FloodingService floodingService;

	@GetMapping("/{id}")
	public ResponseEntity<Response> getOne(@PathVariable("id") Integer id) {
		Flooding flooding = floodingService.getOneById(id);
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (flooding == null) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
			response.setCode(code);
			response.setMessage(message);
			return ResponseEntity.ok(response);
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(Arrays.asList(flooding));

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PaginationResponse> getFloodingsByCriteria(@RequestParam(required = false) Date disasterDate,
			@RequestParam(required = false) Integer locationId, @RequestParam Integer pageNumber) {
		Page<Flooding> flooding = floodingService.getFloodingsByCriteria(locationId, disasterDate, pageNumber - 1);
		PaginationResponse response = new PaginationResponse();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (!flooding.hasContent()) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setPage(flooding.getNumber() + 1);
		response.setPageSize(flooding.getSize());
		response.setTotalAllData(flooding.getNumberOfElements());
		response.setTotalPages(flooding.getTotalPages());
		response.setData(flooding.getContent());

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response> saveOne(@RequestBody Flooding flooding) throws Exception {
		Flooding newFlooding = floodingService.addOne(flooding);
		Response resp = new Response();

		if (newFlooding != null) {
			resp.setCode(String.valueOf(HttpStatus.CREATED.value()));
			resp.setMessage("Sucessfully Save!");
			resp.setData(Arrays.asList(newFlooding));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteAlumniById(@PathVariable Integer id) {
		floodingService.deleteById(id);
		Response resp = new Response();
		resp.setCode(String.valueOf(HttpStatus.OK.value()));
		resp.setMessage("Sucessfully Delete");

		return ResponseEntity.status(HttpStatus.OK).body(resp);
	}

	private Pageable getPagination(Integer page, Integer pageSize) {
		if (page == null || pageSize == null)
			return null;

		return PageRequest.of(page, pageSize, null);
	}
}
