package com.mysociety.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Roles")
public class Role {

	@Id
	@Column(name="roleid")
	private long roleid;
	@Column(name="rolename")
	private String rolename;
	
	@OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "roles")
	private Set<UserRole> useroles = new HashSet<>();
	
	
	public Role(long roleid, String rolename) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
	}

	public long getRoleid() {
		return roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

//	public Set<UserRole> getUseroles() {
//		return useroles;
//	}
//
//	public void setUseroles(Set<UserRole> useroles) {
//		this.useroles = useroles;
//	}
	
}
