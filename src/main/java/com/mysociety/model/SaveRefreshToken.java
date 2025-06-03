package com.mysociety.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="saverefreshtoken")
public class SaveRefreshToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="refreshid")
	private long refreshTokenid;

	@Column(name="userid")
	private long userId;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="refreshtoken")
	private String refreshToken;
	
	@Column(name="issudate")
	private LocalDateTime issueDate;

	@Column(name="expirydate")
	private LocalDateTime expiryDate;

	
	
	public SaveRefreshToken() {
		super();
	}



	public SaveRefreshToken(long refreshTokenid, long userId, String userName, String refreshToken,
			LocalDateTime issueDate, LocalDateTime expiryDate) {
		super();
		this.refreshTokenid = refreshTokenid;
		this.userId = userId;
		this.userName = userName;
		this.refreshToken = refreshToken;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
	}



	public long getRefreshTokenid() {
		return refreshTokenid;
	}



	public void setRefreshTokenid(long refreshTokenid) {
		this.refreshTokenid = refreshTokenid;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getRefreshToken() {
		return refreshToken;
	}



	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}



	public LocalDateTime getIssueDate() {
		return issueDate;
	}



	public void setIssueDate(LocalDateTime issueDate) {
		this.issueDate = issueDate;
	}



	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}



	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	

	
	
}
