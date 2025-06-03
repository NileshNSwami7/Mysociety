package com.mysociety.service;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import com.mysociety.model.User;
import com.mysociety.model.Userrole;

@Service
public interface Userservice {

	public User createUser(User user,Set<Userrole>userrole);

	public ResponseEntity<?> getUser(String username,String email);
	
	public ResponseEntity<?> updateGoogleUser(User user,Set<Userrole>userrole);

	public ResponseEntity<?> deleteUser(Long userid);

	public boolean findUser(String email);
}
