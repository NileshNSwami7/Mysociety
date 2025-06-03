package com.mysociety.security;

import jakarta.servlet.http.Cookie;

public class JwtResponse {
	
	private Cookie token;
	private Cookie refreshtoken;

	

	public JwtResponse(Cookie token) {
		super();
		this.token = token;
	}
	
	public JwtResponse(Cookie access, Cookie refresh) {
		super();
		this.token = access;
		this.refreshtoken = refresh;
	}

	public Cookie getToken() {
		return token;
	}

	public void setToken(Cookie token) {
		this.token = token;
	}

	public Cookie getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(Cookie refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	
	
}
