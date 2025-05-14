package com.mysociety.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mysociety.serviceimpl.UserSecurityServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtutils;
	
	@Autowired
	private UserSecurityServiceImpl userservicefilter;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authheader = request.getHeader("Authorization");
		String username = null;
		String token=null;
		
		if(authheader != null && authheader.startsWith("Bearer ")) {
			token = authheader.substring(7);
			
			try {
				username = jwtutils.extractUsername(token);
			}catch(ExpiredJwtException e) {
				e.printStackTrace();
				System.out.println("Token has expired");
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("error");
			}
		}
		else {
			System.out.println("Invalid token not started with Bearer string");
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userdtails = this.userservicefilter.loadUserByUsername(username);
			if(jwtutils.validateToken(token, username)) {
				
				UsernamePasswordAuthenticationToken usernampasswordauthetication = 
						new UsernamePasswordAuthenticationToken(userdtails,null,userdtails.getAuthorities());
				
				usernampasswordauthetication.setDetails(new WebAuthenticationDetailsSource()
						.buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernampasswordauthetication);
			}
			else {
				System.out.println("Token is not valid");
			}
		}
		filterChain.doFilter(request, response);
	}
	
	
}
