package com.mysociety.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.mysociety.model.SaveRefreshToken;
import com.mysociety.model.User;
import com.mysociety.repository.SaveRefreshTokenRepository;
import com.mysociety.repository.Userrepository;
import com.mysociety.service.SaveRefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class SaveRefreshTokenImpl implements SaveRefreshTokenService{

	
    
    @Autowired
    private SaveRefreshTokenRepository tokenrepo;
    
	@Autowired
	private Userrepository userrepo;
	
	

   
	
	@Override
	public SaveRefreshToken saveResfreshToken(String username, String resfreshtoken) {
		
		User user = this.userrepo.findByUsername(username);
		SaveRefreshToken reftoken = new SaveRefreshToken();
		if(user!= null) {
			reftoken.setUserName(user.getUsername());
			reftoken.setUserId(user.getUserid());
			reftoken.setRefreshToken(resfreshtoken);
			reftoken.setIssueDate(LocalDateTime.now());
			reftoken.setExpiryDate(LocalDateTime.now().plusMinutes(5));
			return this.tokenrepo.save(reftoken);
		}
		else {
			return reftoken;
		}
//		return this.tokenrepo.save(reftoken);
		
	}
	

	public boolean isrefreshTokenValidate(String refreshtoken) {
		Optional<SaveRefreshToken> refretoken = this.tokenrepo.findByRefreshToken(refreshtoken);
		this.tokenrepo.deleallExpiredTokens(LocalDateTime.now(),refretoken.get().getUserId());
		return true;
	}
	
	public ResponseEntity<?> deletelogoutToken(String tokenrequest){
		long id = this.tokenrepo.deletedByToken(tokenrequest);
		if(id>0) {
			return ResponseEntity.ok("Token deleted Successfully");
		}else {
			return  ResponseEntity.status(401).body("not deleted");
		}
	}
}
