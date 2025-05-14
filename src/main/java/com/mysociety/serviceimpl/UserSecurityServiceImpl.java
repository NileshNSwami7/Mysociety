package com.mysociety.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysociety.model.User;
import com.mysociety.repository.Userrepository;
import com.mysociety.security.UserSecurityDetails;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {

	@Autowired
	private Userrepository userrepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userrepo.findByUsername(username);
		
		if(user == null ) {
			System.out.println("User not found");
			throw new UsernameNotFoundException(username);
		}
		
		return new UserSecurityDetails(user);
	}

}
