package com.project.assetpln.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.assetpln.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

}
