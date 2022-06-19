package com.project.assetpln.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.GarduInduk;
import com.project.assetpln.model.Location;
import com.project.assetpln.model.UploadHistory;
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

	public Page<Flooding> getFloodingsByCriteria(Integer locationId, Date disasterDate, Integer pageNumber) {
		Pageable pagination = getPagination(pageNumber, 10);
		Page<Flooding> result = floodingRepository.findByCriteriaPaging(locationId, disasterDate, pagination); 
		return result;
	}

	private Pageable getPagination(Integer page, Integer pageSize) {
		if (page == null || pageSize == null)
			return null;

		return PageRequest.of(page, pageSize, Sort.by("id").ascending());
	}

	public Flooding getOneById(Integer id) {
		return floodingRepository.findById(id).orElse(null);
	}

	public void deleteById(Integer id) {
		floodingRepository.deleteById(id);
	}
}
