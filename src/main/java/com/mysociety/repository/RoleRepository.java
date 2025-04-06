package com.mysociety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysociety.model.Role;

public interface Rolerepository extends JpaRepository<Role, Long>{

}
