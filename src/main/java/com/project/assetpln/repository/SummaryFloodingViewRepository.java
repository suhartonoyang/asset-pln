package com.project.assetpln.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.assetpln.model.SummaryFloodingView;
import com.project.assetpln.model.SummaryFloodingViewId;
import com.project.assetpln.model.UploadHistory;

@Repository
public interface SummaryFloodingViewRepository extends JpaRepository<SummaryFloodingView, SummaryFloodingViewId> {

	@Query(nativeQuery = true, value = "SELECT * FROM SUMMARY_FLOODING_V WHERE LOCATION_ID = COALESCE(?1, LOCATION_ID) AND YEAR = COALESCE(?2, YEAR)")
	public List<SummaryFloodingView> findByCriteriaPaging(Integer locationId, Integer year);

}
