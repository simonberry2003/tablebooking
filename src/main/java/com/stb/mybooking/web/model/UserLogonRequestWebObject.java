package com.stb.mybooking.web.model;

public class UserLogonRequestWebObject {
	private String emailAddress;
	private String password;

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
