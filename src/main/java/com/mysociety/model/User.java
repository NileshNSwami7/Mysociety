package com.mysociety.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="userid")
	private long userid;
	@Column(name="username")
	private String username;
	@Column(name="firstname")
	private String firstname;
	@Column(name="lastname")
	private String lastname;
	@Column(name="email")
	private String email;
	@Column(name="contactno")
	private String mobileno;
	@Column(name="password")
	private String password;
	@Column(name="profilestatus", nullable = false)
	private boolean profileStatus;
	@Column(name="profile")
	private String profile;
	@Column(name="Date")
	private LocalDate date;
	@Column(name="address")
	private String address;
	@Column(name="flatno")
	private String flatno;
	@Column(name="documentfile",columnDefinition="LONGBLOB")
	@Lob
	private byte[] documentfile;
	@Column(name="ProvidedBy")
	private String providedBy;
	
	@ManyToOne
	private Role role;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "users")
	@JsonIgnore
	private Set<Userrole> userroles = new HashSet<>();
	
	

	public Set<Userrole> getUserroles() {
		return userroles;
	}

	public void setUserroles(Set<Userrole> userroles) {
		this.userroles = userroles;
	}

	public User(long userid, String username, String firstname, String lastname, String email, String mobileno,
			String password, boolean profileStatus, String profile, LocalDate date, String address, String flatno,
			byte[] documentfile, Set<Userrole> userroles) {
		super();
		this.userid = userid;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.mobileno = mobileno;
		this.password = password;
		this.profileStatus = profileStatus;
		this.profile = profile;
		this.date = date;
		this.address = address;
		this.flatno = flatno;
		this.documentfile = documentfile;
		this.userroles = userroles;
	}

	public User() {
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isProfileStatus() {
		return profileStatus;
	}

	public void setProfileStatus(boolean profileStatus) {
		this.profileStatus = profileStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFlatno() {
		return flatno;
	}

	public void setFlatno(String flatno) {
		this.flatno = flatno;
	}

	public byte[] getDocumentfile() {
		return documentfile;
	}

	public void setDocumentfile(byte[] documentfile) {
		this.documentfile = documentfile;
	}
	
	public String getProvidedBy() {
		return providedBy;
	}

	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



}
