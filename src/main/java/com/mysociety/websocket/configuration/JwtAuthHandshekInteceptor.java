package com.mysociety.websocket.configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.mysociety.security.JwtUtils;
import com.mysociety.serviceimpl.UserSecurityServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthHandshekInteceptor implements HandshakeInterceptor {
	
	@Autowired
	private JwtUtils jwtutils;

	@Autowired
	private UserSecurityServiceImpl securityserviceimpl;
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
	
		if(request instanceof ServletServerHttpRequest servletrequest) {
			HttpServletRequest httpreq = servletrequest.getServletRequest();
			Cookie[] cookies = httpreq.getCookies();
			if(cookies != null) {
				for(Cookie cookie : cookies) {
					if(cookie.getName().equals("access_token")) {
						String username= jwtutils.extractUsername(cookie.getValue());
						if(jwtutils.validateToken(cookie.getValue(),username))
						{
							attributes.put("username",username);
							return true;
						}
					}
				}
			}
		
		}
		System.out.println("Websocket Auth failed: token missing");
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
		System.out.println("🔐 Handshake called for WebSocket...");
		
	}

}
