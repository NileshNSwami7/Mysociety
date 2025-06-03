package com.mysociety.security;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.mysociety.model.User;
import com.mysociety.repository.Userrepository;
import com.mysociety.service.Userservice;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
//	@Autowired
//	private Userservice userservice;
	
	@Autowired
	public Userrepository userrepository;
	
	Logger logger = LoggerFactory.getLogger(OAuthAthenticationSuccessHandler.class);
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
		
		Optional<User> isuserPresent=this.userrepository.findByEmail(user.getAttribute("email"));
		
		if(isuserPresent.isPresent()) {
			new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:4200/googleuser");
		}else {
			User usr = new User();
			usr.setFirstname(user.getAttribute("given_name"));
			usr.setLastname(user.getAttribute("family_name"));
			usr.setUsername(user.getAttribute("name"));
			usr.setEmail(user.getAttribute("email"));
			usr.setProfile(user.getAttribute("picture"));
			usr.setProfileStatus(false);
			usr.setProvidedBy("Google");
			
			this.userrepository.save(usr);
			new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:4200/googleuser");
		}
		
//		logger.info(user.getName());
//		
//		user.getAttributes().forEach((key,value)->{
//			logger.info("{}:{}",key ,value);
//		});
//		
//		logger.info(user.getAuthorities().toString());

	}

}
