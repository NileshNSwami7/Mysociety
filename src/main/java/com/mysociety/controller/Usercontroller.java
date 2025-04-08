package com.mysociety.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysociety.model.Role;
import com.mysociety.model.User;
import com.mysociety.model.Userrole;
import com.mysociety.service.Userservice;

@RestController
@RequestMapping("/mysociety/api/user")
public class Usercontroller {

	@Autowired
	private Userservice userservice;
	
	@PostMapping("/create-user")
	public User createUser(@RequestBody User user) throws Exception {
		
		Set<Userrole> uroles = new HashSet<>();
		Role roles = new Role();
		roles.setRoleid(102);
		roles.setRolename("Family");
		
		Userrole userroles = new Userrole();
		userroles.setUser(user);
		userroles.setRoles(roles);
		
		uroles.add(userroles);
		return this.userservice.createUser(user, uroles);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getUserdetails(@PathVariable("username") String username) {
		return this.userservice.getUser(username);
	}
	
	@DeleteMapping("/{userid}")
	public ResponseEntity<?> deletUser(@PathVariable("userid") Long userid){
		return this.userservice.deleteUser(userid);
	}
}
