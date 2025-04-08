package com.mysociety.serviceimpl;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysociety.exception.Mysocietyexception;
import com.mysociety.model.User;
import com.mysociety.model.Userrole;
import com.mysociety.repository.Rolerepository;
import com.mysociety.repository.Userrepository;
import com.mysociety.service.Userservice;

import jakarta.transaction.Transactional;

@Service

public class Userserviceimpl implements Userservice {

	@Autowired
	public Userrepository userrepository;
	
	@Autowired
	public Rolerepository rolerepository;

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
				localuser = this.userrepository.save(user);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			throw new Mysocietyexception("User is already present...!");

		}
		return localuser;
	}
}
