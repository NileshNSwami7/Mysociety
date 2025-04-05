package com.mysociety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysociety.model.User;

public interface userrepository extends JpaRepository<User, Long>{

}
