package com.project.assetpln.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.assetpln.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
	
	public List<User> findByRole(String role);
}
