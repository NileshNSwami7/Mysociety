package com.mysociety.exception;

import java.util.Date;

public class ErrorDetails {
	private int status;
	private Date timestap;
	private String message;
	
	public ErrorDetails(int status,Date timestap, String message) {
		super();
		this.status = status;
		this.timestap = timestap;
		this.message = message;
	}
	
	public ErrorDetails() {
		super();
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimestap() {
		return timestap;
	}
	public void setTimestap(Date timestap) {
		this.timestap = timestap;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
