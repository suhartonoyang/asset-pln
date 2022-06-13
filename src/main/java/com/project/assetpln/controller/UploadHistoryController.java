package com.project.assetpln.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.annotation.MultipartConfig;

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
import org.springframework.web.multipart.MultipartFile;

import com.project.assetpln.bean.Response;
import com.project.assetpln.model.AesConfiguration;
import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.Location;
import com.project.assetpln.model.UploadHistory;
import com.project.assetpln.model.User;
import com.project.assetpln.repository.FloodingRepository;
import com.project.assetpln.service.FloodingService;
import com.project.assetpln.service.LocationService;
import com.project.assetpln.service.UploadHistoryService;

@RestController
@RequestMapping("${rest.prefix:}/upload-histories")
public class UploadHistoryController {

	@Autowired
	private UploadHistoryService uploadHistoryService;

	@GetMapping("/{id}")
	public ResponseEntity<Response> getOne(@PathVariable("id") Integer id) {
		UploadHistory uploadHistory = uploadHistoryService.getOneById(id);
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (uploadHistory == null) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(Arrays.asList(uploadHistory));

		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Response> getUploadHistoriesByCriteria(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Date uploadDate, @RequestParam(required = false) Integer locationId) {
		List<UploadHistory> uploadHistories = uploadHistoryService.getUploadHistoriesByCriteria(id, uploadDate, locationId);
		Response response = new Response();
		String code = String.valueOf(HttpStatus.OK.value());
		String message = HttpStatus.OK.name();

		if (uploadHistories.isEmpty()) {
			code = String.valueOf(HttpStatus.NOT_FOUND.value());
			message = "No Data Found";
		}

		response.setCode(code);
		response.setMessage(message);
		response.setData(uploadHistories);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response> saveOne(@RequestParam String username, @RequestParam Integer locationId,
			@RequestParam MultipartFile file) throws Exception {
		UploadHistory newUploadHistory = uploadHistoryService.upload(username.toUpperCase(), locationId, file);
		Response resp = new Response();

		if (newUploadHistory != null) {
			resp.setCode(String.valueOf(HttpStatus.CREATED.value()));
			resp.setMessage("Sucessfully Upload!");
			resp.setData(Arrays.asList(newUploadHistory));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}
}
