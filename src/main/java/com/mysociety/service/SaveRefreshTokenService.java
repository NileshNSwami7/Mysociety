package com.mysociety.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mysociety.model.SaveRefreshToken;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface SaveRefreshTokenService {
	
	public SaveRefreshToken saveResfreshToken(String username , String resfreshtoken);

	public boolean isrefreshTokenValidate(String request);

	public ResponseEntity<?> deletelogoutToken(String  tokenrequest); 
}
