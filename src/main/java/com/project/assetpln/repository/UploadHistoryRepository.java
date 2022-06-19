package com.project.assetpln.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.assetpln.model.UploadHistory;

@Repository
public interface UploadHistoryRepository extends JpaRepository<UploadHistory, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM UPLOAD_HISTORY WHERE ID = COALESCE(?1, ID) AND UPLOAD_DATE = COALESCE(?2, UPLOAD_DATE) AND LOCATION_ID = COALESCE(?3, LOCATION_ID)")
	public List<UploadHistory> findByCriteria(Integer id, Date uploadDate, Integer locationId);
	
	@Query(nativeQuery = true, value = "SELECT * FROM UPLOAD_HISTORY WHERE ID = COALESCE(?1, ID) AND UPLOAD_DATE = COALESCE(?2, UPLOAD_DATE) AND LOCATION_ID = COALESCE(?3, LOCATION_ID)")
	public Page<UploadHistory> findByCriteriaPaging(Integer id, Date uploadDate, Integer locationId, Pageable pageable);

}
