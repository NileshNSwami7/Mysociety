package com.mysociety.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.mysociety.model.User;
import com.mysociety.model.Userrole;

@Service
public interface Userservice {

	public User createUser(User user,Set<Userrole>userrole);
}
