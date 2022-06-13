package com.project.assetpln.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.assetpln.bean.Response;
import com.project.assetpln.model.AesConfiguration;
import com.project.assetpln.model.GarduInduk;
import com.project.assetpln.model.Location;
import com.project.assetpln.service.GarduIndukService;
import com.project.assetpln.service.LocationService;

@RestController
@RequestMapping("${rest.prefix:}/gardu-induks")
public class GarduIndukController {

	@Autowired
	private GarduIndukService garduIndukService;
	
	@GetMapping
	public ResponseEntity<Response> getAllGarduInduks() {
		List<GarduInduk> garduInduks = garduIndukService.getAllGarduInduks();
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (garduInduks.isEmpty()) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(garduInduks);

		return ResponseEntity.ok(response);
	}
}
