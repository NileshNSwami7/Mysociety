package com.mysociety.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysociety.security.JwtRequest;
import com.mysociety.security.JwtResponse;
import com.mysociety.security.JwtUtils;
import com.mysociety.security.UserSecurityDetails;
import com.mysociety.serviceimpl.UserSecurityServiceImpl;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	@Autowired
	private UserSecurityServiceImpl securityServiceimpl;
	
	@Autowired
	private JwtUtils jwtutils;
	
	
	@PostMapping("/generate-token")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<?> gentoken(@RequestBody JwtRequest jwtrequest) throws Exception{
		try {
			authenticate(jwtrequest.getUsername(), jwtrequest.getPassword());
		}catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}
		
		UserSecurityDetails userdetails = (UserSecurityDetails) securityServiceimpl.loadUserByUsername(jwtrequest.getUsername());
		String token = jwtutils.generateToken(userdetails.getUsername());
		return ResponseEntity.ok(new JwtResponse(token));
		
	}
	
	private void authenticate(String username,String password) throws Exception {
		
		try {
			authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e) {
			e.printStackTrace();
			System.out.println("User disabled" + e.getMessage());
		}catch(BadCredentialsException e) {
			e.printStackTrace();
			System.out.println("Invalid creadentials" + e.getMessage());
		}
	}

}
