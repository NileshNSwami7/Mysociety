package com.mysociety.controller;

import java.security.Principal;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysociety.exception.MessageResponse;
import com.mysociety.security.HttpCookies;
import com.mysociety.security.JwtRequest;
import com.mysociety.security.JwtUtils;
import com.mysociety.security.UserSecurityDetails;
import com.mysociety.service.SaveRefreshTokenService;
import com.mysociety.serviceimpl.UserSecurityServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationmanager;
	
	@Autowired
	private UserSecurityServiceImpl securityServiceimpl;
	
	@Autowired
	private JwtUtils jwtutils;
	
	@Autowired
	private HttpCookies cookies;
	
	@Autowired
	private SaveRefreshTokenService saverefrestokenservice;
	
    private static final long EXPIRATION_TIME_FOR_ACCESS_TOKEN = 1000 * 60 * 2; // 10 min 1000 * 60 * 60; // 1 hour

	
	@PostMapping("/generate-token")
	@CrossOrigin(origins="http://localhost:4200", allowCredentials ="true")
	public ResponseEntity<?> gentoken(@RequestBody JwtRequest jwtrequest,HttpServletResponse response) throws Exception{
		Authentication authentication;
		try {
			authentication =authenticate(jwtrequest.getUsername(), jwtrequest.getPassword());
		}catch(UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User not found");
		}

		UserSecurityDetails userdetails = (UserSecurityDetails) securityServiceimpl.loadUserByUsername(authentication.getName());
		String token = jwtutils.generateToken(userdetails.getUsername(),userdetails.getAuthorities(), 2 * 60 * 1000 ); // this is accessed token
		String refreshtoken = jwtutils.generateToken(userdetails.getUsername(),userdetails.getAuthorities(), 5 * 60 * 1000 );// this is refresh token
		
		String username = jwtrequest.getUsername();
		this.saverefrestokenservice.saveResfreshToken(username,refreshtoken);
		// Add token in cookie 
		
		/*Cookie access = cookies.createCookies("access_token", token, 600000);
		Cookie refresh = cookies.createCookies("refresh_token", refreshtoken,604800000);
		
		response.addCookie(access);
		response.addCookie(refresh);
		
		return ResponseEntity.ok(new JwtResponse(access,refresh));*/
		
		// Cookie does not support SameSite and Priority so it add manually
		
		response.addHeader("Set-Cookie", "access_token=" + token +
		        "; Max-Age=120; Path=/; HttpOnly; SameSite=Lax; Priority=High");

		response.addHeader("Set-Cookie", "refresh_token=" + refreshtoken +
		        "; Max-Age=300; Path=/; HttpOnly;  SameSite=Lax; Priority=High");
		
//		 Map<String, Object> body = new HashMap<>();
//		    body.put("exp", EXPIRATION_TIME_FOR_ACCESS_TOKEN);
//		return ResponseEntity.ok(Map.of(
//        "username", userdetails.getUsername(),
//        "roles", userdetails.getAuthorities()
//    ));
    
    return ResponseEntity.ok(new MessageResponse("Login Successfully"));
	}
	
	//refresh token is used to generate new access token 
	@GetMapping("/refresh-access-token")
	public ResponseEntity<?>refreshAccessToken(HttpServletRequest request, HttpServletResponse response){
		String refreshtoken = cookies.getCookies(request,"refresh_token");
		
		if(refreshtoken == null) {
//			this.saverefrestokenservice.isrefreshTokenValidate(refreshtoken);
			return ResponseEntity.status(401).body("No refresh token");
		}
		
		try {
			if(this.saverefrestokenservice.isrefreshTokenValidate(refreshtoken)) {
				String username = jwtutils.extractUsername(refreshtoken);
				UserSecurityDetails userSecurityDetails = (UserSecurityDetails)securityServiceimpl.loadUserByUsername(username);
				String newaccestoken = jwtutils.generateToken(username,userSecurityDetails.getAuthorities(), 2 * 60 * 1000 );
//				cookies.addCookie(response,"acces_token",newaccestoken,1200);
				response.addHeader("Set-Cookie", "access_token=" + newaccestoken +
				        "; Max-Age=120; Path=/; HttpOnly; SameSite=Lax; Priority=High");
				return ResponseEntity.ok(Map.of("message", "Access token refreshed"));
			}
			else {
				return ResponseEntity.status(401).body("Invalid refresh token");
			}
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid refresh token");
		}
	}
	
	private Authentication authenticate(String username,String password) throws Exception {
		
		try {
			return authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(DisabledException e) {
			throw new Exception("User disabled " + e);
		}catch(BadCredentialsException e) {
			throw new Exception("Invalid creadentials " + e);
		}
	}
	
	
	@GetMapping("/currentuser")
	public UserSecurityDetails getCurrentUser(@AuthenticationPrincipal UserSecurityDetails securitydetails) {
		UserSecurityDetails user = ((UserSecurityDetails)this.securityServiceimpl.loadUserByUsername(securitydetails.getUsername()));
		return user;
	}
	
	@PostMapping("/logoutuser")
	public ResponseEntity<?> logOutUser(HttpServletRequest request,HttpServletResponse response){
		String refreshtoken = cookies.getCookies(request,"refresh_token");
		this.saverefrestokenservice.deletelogoutToken(refreshtoken);
		
		HttpSession session = request.getSession(false); // Don't create if not exists
	    if (session != null) {
	        session.invalidate(); // Invalidate the session
	    }

	    // Clear cookie
	    Cookie cookie = new Cookie("JSESSIONID", null);
	    cookie.setPath("/");
	    cookie.setHttpOnly(true);
	    cookie.setMaxAge(0);
	    response.addCookie(cookie);

		return ResponseEntity.ok(new MessageResponse("Logout Successfully")) ;
	}

}
