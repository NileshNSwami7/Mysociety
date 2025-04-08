package com.mysociety.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mysociety.model.User;
import com.mysociety.model.Userrole;

@Service
public interface Userservice {

	public User createUser(User user,Set<Userrole>userrole);

	public ResponseEntity<?> getUser(String username);

	public ResponseEntity<?> deleteUser(Long userid);
}
