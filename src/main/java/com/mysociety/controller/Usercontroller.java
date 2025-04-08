package com.mysociety.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysociety.model.Role;
import com.mysociety.model.User;
import com.mysociety.model.Userrole;
import com.mysociety.service.Userservice;

@RestController
@RequestMapping("/mysociety/user")
public class Usercontroller {

	@Autowired
	private Userservice userservice;
	
	@PostMapping("/create-user")
	public User createUser(@RequestBody User user) throws Exception {
		
		Set<Userrole> uroles = new HashSet<>();
		Role roles = new Role();
		roles.setRoleid(101);
		roles.setRolename("Admin");
		
		Userrole userroles = new Userrole();
		userroles.setUser(user);
		userroles.setRoles(roles);
		
		uroles.add(userroles);
		return this.userservice.createUser(user, uroles);
	}
}
