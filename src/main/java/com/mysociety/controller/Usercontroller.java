package com.mysociety.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PutExchange;

import com.mysociety.helper.Fileuploader;
import com.mysociety.model.Role;
import com.mysociety.model.User;
import com.mysociety.model.Userrole;
import com.mysociety.service.Userservice;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping("/mysociety/api/user")

public class Usercontroller {

	@Autowired
	private Userservice userservice;
	
	@Autowired
	private Fileuploader fileup;
	
	@PostMapping(value="/create-user",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public User createUser( @RequestPart User user,
	        @RequestPart MultipartFile file
			) throws Exception {
			
		if(file.isEmpty()) {
			throw new FileNotFoundException();
		}else {
			System.out.println(file.getName() +"\n"+file.getContentType()+"\n"+file.getOriginalFilename());
			user.setDocumentfile(file.getBytes());
			fileup.fileuploads(file);
			
		}
		Set<Userrole>uroles = new HashSet<>();
		Role roles = new Role();

		roles.setRoleid(user.getRole().getRoleid());
		roles.setRolename(user.getRole().getRolename());
		
		
		Userrole userroles = new Userrole();
		userroles.setUser(user);
		userroles.setRoles(roles);
		
		uroles.add(userroles);
		return this.userservice.createUser(user, uroles);
	}
	
//	@GetMapping("/{username}")
//	public ResponseEntity<?> getUserdetails(@PathVariable("username") String username) {
//		return this.userservice.getUser(username);
//	}
//	
	@DeleteMapping("/{userid}")
	public ResponseEntity<?> deletUser(@PathVariable("userid") Long userid){
		return this.userservice.deleteUser(userid);
	}
	
	@GetMapping("/getuser-info")
	public ResponseEntity<?> getUserdetails(Authentication authentication) {
		DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
		return this.userservice.getUser(user.getAttribute("name"),user.getAttribute("email"));
	}
	
	@PutMapping(value="/add-googleuser-info",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?>updateUser(@RequestPart User user, @RequestPart MultipartFile file) throws IOException{
		
		if(file.isEmpty()) {
			throw new FileNotFoundException();
		}else {
			System.out.println(file.getName() +"\n"+file.getContentType()+"\n"+file.getOriginalFilename());
			user.setDocumentfile(file.getBytes());
			fileup.fileuploads(file);
			
		}
		Set<Userrole>uroles = new HashSet<>();
		Role roles = new Role();

		roles.setRoleid(user.getRole().getRoleid());
		roles.setRolename(user.getRole().getRolename());
		
		
		Userrole userroles = new Userrole();
		userroles.setUser(user);
		userroles.setRoles(roles);
		
		uroles.add(userroles);
		return this.userservice.updateGoogleUser(user, uroles);
	}
}
