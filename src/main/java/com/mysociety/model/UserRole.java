package com.mysociety.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
public class Userrole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userroleid;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User users;
	
	@ManyToOne
	private Role roles;

	public long getUserroleid() {
		return userroleid;
	}

	public void setUserroleid(long userroleid) {
		this.userroleid = userroleid;
	}

	public User getUser() {
		return users;
	}

	public void setUser(User users) {
		this.users = users;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}
	
	
}
