package com.project.assetpln.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.assetpln.bean.Response;
import com.project.assetpln.model.AesConfiguration;
import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.Location;
import com.project.assetpln.model.User;
import com.project.assetpln.repository.FloodingRepository;
import com.project.assetpln.service.FloodingService;
import com.project.assetpln.service.LocationService;

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
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(Arrays.asList(flooding));

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Response> getFloodingsByCriteria(@RequestParam(required = false) Date disasterDate,
			@RequestParam(required = false) Integer locationId) {
		List<Flooding> floodings = floodingService.getFloodingsByCriteria(disasterDate, locationId);
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (floodings.isEmpty()) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(floodings);

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
}
