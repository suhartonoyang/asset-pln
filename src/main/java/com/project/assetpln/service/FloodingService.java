package com.project.assetpln.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.GarduInduk;
import com.project.assetpln.model.Location;
import com.project.assetpln.repository.FloodingRepository;
import com.project.assetpln.repository.GarduIndukRepository;
import com.project.assetpln.repository.LocationRepository;

@Service
public class FloodingService {

	@Autowired
	private FloodingRepository floodingRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private GarduIndukRepository garduIndukRepository;

	public Flooding addOne(Flooding flooding) {
		Date date = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		System.out.println(date);
		
		flooding.setCreatedDate(date);
		
		GarduInduk garduInduk = new GarduInduk();
		if (flooding.getGarduInduk() != null) {
			garduInduk = garduIndukRepository.findById(flooding.getGarduInduk().getId()).orElse(null);
			flooding.setGarduInduk(garduInduk);
		}

		Location location = new Location();
		if (flooding.getLocation() != null) {
			location = locationRepository.findById(flooding.getLocation().getId()).orElse(null);
			flooding.setLocation(location);
		}

		flooding.setPenyulang(flooding.getPenyulang().toUpperCase());
		return floodingRepository.save(flooding);
	}

	public List<Flooding> getFloodingsByCriteria(Date disasterDate, Integer locationId) {
		List<Flooding> floodings = new ArrayList<Flooding>();

		if (locationId == null && disasterDate == null) {
			floodings = floodingRepository.findAll();
		}

		if (locationId != null && disasterDate == null) {
			floodings = floodingRepository.findByLocation_Id(locationId);
		}

		if (locationId == null && disasterDate != null) {
			floodings = floodingRepository.findByDisasterDate(disasterDate);
		}

		if (locationId != null && disasterDate != null) {
			floodings = floodingRepository.findByLocation_IdAndDisasterDate(locationId, disasterDate);
		}

		return floodings;

	}

	public Flooding getOneById(Integer id) {
		return floodingRepository.findById(id).orElse(null);
	}
}
