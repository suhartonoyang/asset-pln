package com.project.assetpln.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.assetpln.bean.SummaryFlooding;
import com.project.assetpln.bean.SummaryFloodingPerLocation;
import com.project.assetpln.bean.SummaryFloodingPerYear;
import com.project.assetpln.model.SummaryFloodingView;
import com.project.assetpln.model.UploadHistory;
import com.project.assetpln.repository.SummaryFloodingViewRepository;

import net.bytebuddy.build.Plugin.Engine.Summary;

@Service
public class SummaryFloodingViewService {

	@Autowired
	private SummaryFloodingViewRepository summaryFloodingViewRepository;

	public SummaryFlooding getSummaryFloodingByCriteria(Integer locationId, Integer year) {
		List<SummaryFloodingView> views = summaryFloodingViewRepository.findByCriteriaPaging(locationId, year);
		System.out.println("query views");
		views.forEach(p -> {
			System.out.println(p);
		});

		if (views.isEmpty()) {
			return null;
		}

		List<Integer> locationIds = views.stream().map(m -> m.getLocationId()).distinct().collect(Collectors.toList());
		List<Integer> years = views.stream().map(m -> m.getYear()).distinct().collect(Collectors.toList());

		List<SummaryFloodingPerLocation> perLocations = new ArrayList<SummaryFloodingPerLocation>();
		SummaryFlooding summaryFlooding = new SummaryFlooding();

		int rownum = 1;
		for (Integer locId : locationIds) {
			List<SummaryFloodingView> viewLocations = views.stream().filter(f -> f.getLocationId() == locId).collect(Collectors.toList());
//			System.out.println(rownum);
//			viewLocations.forEach(p -> {
//				System.out.println(p);
//			});

			SummaryFloodingPerLocation perLocation = new SummaryFloodingPerLocation();
			perLocation.setNo(rownum);
			perLocation.setLocationId(locId);

			String locationName = viewLocations.stream().map(m -> m.getLocationName()).distinct().findFirst().orElse(null);
			perLocation.setLocationName(locationName);

			List<SummaryFloodingPerYear> perYears = new ArrayList<SummaryFloodingPerYear>();
//			System.out.println("loop year");
			for (Integer y : years) {
//				System.out.println(y);

				SummaryFloodingPerYear perYear = new SummaryFloodingPerYear();
				perYear.setYear(y);

				int totalGarduSubmerged = viewLocations.stream().filter(f -> f.getYear().intValue() == y)
						.mapToInt(m -> m.getTotalGarduSubmerged()).sum();
				perYear.setTotalGarduSubmerged(totalGarduSubmerged);

				int totalNeighbourhoodSubmerged = viewLocations.stream().filter(f -> f.getYear().intValue() == y)
						.mapToInt(m -> m.getTotalNeighbourhoodSubmerged()).sum();
				perYear.setTotalNeighbourhoodSubmerged(totalNeighbourhoodSubmerged);

				perYears.add(perYear);
			}

			perLocation.setYears(perYears);
			perLocations.add(perLocation);
			rownum++;
		}

		// add locations last summary
		SummaryFloodingPerLocation perLocationLast = new SummaryFloodingPerLocation();
		perLocationLast.setNo(null);
		perLocationLast.setLocationName("TOTAL");

		List<SummaryFloodingPerYear> perYearsLast = new ArrayList<SummaryFloodingPerYear>();
		for (Integer y : years) {
			SummaryFloodingPerYear perYearLast = new SummaryFloodingPerYear();
			perYearLast.setYear(y);

			int totalGarduSubmerged = views.stream().filter(f -> f.getYear().intValue() == y).mapToInt(m -> m.getTotalGarduSubmerged())
					.sum();
			perYearLast.setTotalGarduSubmerged(totalGarduSubmerged);

			int totalNeighbourhoodSubmerged = views.stream().filter(f -> f.getYear().intValue() == y)
					.mapToInt(m -> m.getTotalNeighbourhoodSubmerged()).sum();
			perYearLast.setTotalNeighbourhoodSubmerged(totalNeighbourhoodSubmerged);

			perYearsLast.add(perYearLast);
		}
		perLocationLast.setYears(perYearsLast);

		perLocations.add(perLocationLast);

		summaryFlooding.setLocations(perLocations);

		return summaryFlooding;
	}

}