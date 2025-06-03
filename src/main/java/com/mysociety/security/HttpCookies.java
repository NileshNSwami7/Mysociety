package com.mysociety.security;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HttpCookies {
	
	public Cookie createCookies(String name, String value ,int maxtime) {
		Cookie cookie = new Cookie(name,value);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); //set true at production level
		cookie.setPath("/");
		cookie.setMaxAge(maxtime);
		return cookie;
	}
	
	public String getCookies(HttpServletRequest request, String name) {
		 Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (name.equals(cookie.getName())) {
	                    return cookie.getValue();
	                }
	            }
	        }
	        return null;
	}

	public void addCookie(HttpServletResponse response, String name, String newaccesstoken, int maxagetime) {
		Cookie cookie = new Cookie(name,newaccesstoken);
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setPath("/");
		cookie.setMaxAge(maxagetime);
		response.addCookie(cookie);
	}
}
