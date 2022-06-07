package com.project.assetpln.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.assetpln.bean.Response;
import com.project.assetpln.model.AesConfiguration;
import com.project.assetpln.service.AesConfigurationService;

@RequestMapping(value = "/api/aesConfigurations")
@RestController
@CrossOrigin
public class AesConfigurationController {

	@Autowired
	private AesConfigurationService aesConfigurationService;

	@GetMapping
	public ResponseEntity<Response> getAllAesConfigurations() {
		List<AesConfiguration> aesConfigurations = aesConfigurationService.getAllAesConfigurations();
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (aesConfigurations.isEmpty()) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(aesConfigurations);

		return ResponseEntity.ok(response);
	}
}
