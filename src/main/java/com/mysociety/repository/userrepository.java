package com.mysociety.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mysociety.model.User;

public interface Userrepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);

	public Optional<User> findByEmail(String email);

	public User findByUsernameAndEmail(String username, String email);

}
