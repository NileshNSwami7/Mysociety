package com.mysociety.serviceimpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

import com.mysociety.exception.Mysocietyexception;
import com.mysociety.model.User;
import com.mysociety.model.Userrole;
import com.mysociety.repository.Rolerepository;
import com.mysociety.repository.Userrepository;
import com.mysociety.security.UserSecurityDetails;
import com.mysociety.service.Userservice;


@Service
public class Userserviceimpl implements Userservice {

	@Autowired
	public Userrepository userrepository;
	
	@Autowired
	public Rolerepository rolerepository;
	
	
	@Autowired
	public PasswordEncoder passwordencoder;

	@Override
	public User createUser(User user, Set<Userrole> userrole) {
		User localuser = this.userrepository.findByUsername(user.getUsername());
		try {
			if (localuser != null) {
				throw new Mysocietyexception("User is already present...!");
			} else {
				for(Userrole urole:userrole) {
					rolerepository.save(urole.getRoles());
				}
				user.getUserroles().addAll(userrole);
				user.setPassword(passwordencoder.encode(user.getPassword()));
				user.setProvidedBy("User login");
				localuser = this.userrepository.save(user);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			throw new Mysocietyexception("User is already present...!");

		}
		return localuser;
	}
	
	@Override
	public ResponseEntity<?> getUser(String username,String email) {
		Optional<User> usr = Optional.ofNullable(this.userrepository.findByUsernameAndEmail(username,email));
		if(!usr.isPresent()) {
		 throw new Mysocietyexception("User entry is not peresent");
		}
		return new ResponseEntity<>(usr.get(),HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> deleteUser(Long userid){
		Map<String, Boolean> userresponse = new HashMap<>();
		Optional<User> user = this.userrepository.findById(userid);
		if(user.isPresent()) {
			this.userrepository.deleteById(userid);
			userresponse.put("User deleted successfully...!", Boolean.TRUE);
		}else {
			 throw new Mysocietyexception("Invalid Userid...");
		}
		return ResponseEntity.ok(userresponse);
	}
	
	public boolean findUser(String email) {
		
		Optional<User>usr = this.userrepository.findByEmail(email);
		if(usr.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public ResponseEntity<?>updateGoogleUser(User user,Set<Userrole>userrole){
		Optional<User>usr = this.userrepository.findByEmail(user.getEmail());
		if(user == null) {
		return null;
		}else{
			return ResponseEntity.ok(this.userrepository.save(user));
		}
	
		
	}
}
