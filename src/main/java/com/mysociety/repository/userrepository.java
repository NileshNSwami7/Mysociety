package com.mysociety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysociety.model.User;

public interface Userrepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);

}
