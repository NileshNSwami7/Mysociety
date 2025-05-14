package com.mysociety.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

	 private static final String SECRET_KEY = "my_super_secret_key_which_is_at_least_32_chars!";
	    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

	    // HMAC SHA key
	    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

	    // Generate token
	    public String generateToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }

	    // Extract username
	    public String extractUsername(String token) {
	        return parseClaims(token).getSubject();
	    }

	    // Validate token
	    public boolean validateToken(String token, String username) {
	        return extractUsername(token).equals(username) && !isTokenExpired(token);
	    }

	    // Check expiration
	    private boolean isTokenExpired(String token) {
	        return parseClaims(token).getExpiration().before(new Date());
	    }

	    // Parse claims with new API
	    private Claims parseClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    	
	    }
	
}
