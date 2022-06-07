package com.project.assetpln.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.assetpln.model.AesConfiguration;
import com.project.assetpln.repository.AesConfigurationRepository;

@Service
public class AesConfigurationService {

	@Autowired
	private AesConfigurationRepository aesConfigurationRepository;
	
	public List<AesConfiguration> getAllAesConfigurations(){
		return aesConfigurationRepository.findAll();
	}
	
	public AesConfiguration getAesConfigurationById(Integer id) {
		return aesConfigurationRepository.findById(id).orElse(null);
	}
}
