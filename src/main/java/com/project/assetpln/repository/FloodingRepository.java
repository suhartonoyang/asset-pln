package com.project.assetpln.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.UploadHistory;

@Repository
public interface FloodingRepository extends JpaRepository<Flooding, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM FLOODING WHERE LOCATION_ID = COALESCE(?1, LOCATION_ID) AND DISASTER_DATE = COALESCE(?2, DISASTER_DATE)")
	public Page<Flooding> findByCriteriaPaging(Integer LocationId, Date disasterDate, Pageable pageable);

	public List<Flooding> findByLocation_IdAndDisasterDate(int LocationId, Date disasterDate);
}
