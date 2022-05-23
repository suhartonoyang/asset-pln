package com.project.assetpln.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.prefix:/asset-pln/api}")
public class Controller {

	@GetMapping
	public ResponseEntity<String> helloWorld(){
		return ResponseEntity.ok("Hello World");
	}
}
