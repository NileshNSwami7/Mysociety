package com.mysociety.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.mysociety.model.SaveRefreshToken;

@Repository
public interface SaveRefreshTokenRepository extends JpaRepository<SaveRefreshToken, Long>{
	
	public Optional<SaveRefreshToken> findByRefreshToken(String refreshtoken);

	@Modifying
	@Query("delete from SaveRefreshToken r where r.refreshToken = :tokenrequest")
	public int deletedByToken(@Param("tokenrequest") String tokenrequest);

	@Modifying
	@Query("delete from SaveRefreshToken rt where rt.expiryDate <= now and rt.userId =: userId")
	public void deleallExpiredTokens(@Param("now")LocalDateTime now,@Param("userId")long userId);
}
