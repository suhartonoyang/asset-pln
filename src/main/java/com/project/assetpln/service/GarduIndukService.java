package com.project.assetpln.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.assetpln.model.GarduInduk;
import com.project.assetpln.model.Location;
import com.project.assetpln.repository.GarduIndukRepository;
import com.project.assetpln.repository.LocationRepository;

@Service
public class GarduIndukService {

	@Autowired
	private GarduIndukRepository garduIndukRepository;

	public List<GarduInduk> getAllGarduInduks() {
		return garduIndukRepository.findAll().stream().collect(Collectors.toList());
	}
}
