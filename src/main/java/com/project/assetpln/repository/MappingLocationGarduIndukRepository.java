package com.project.assetpln.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.assetpln.model.MappingLocationGarduInduk;

@Repository
public interface MappingLocationGarduIndukRepository extends JpaRepository<MappingLocationGarduInduk, Integer> {

}
