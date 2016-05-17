package com.stb.mybooking.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.MoreObjects;

@Document(collection = "users")
public class UserDomainObject {
	
	@Id
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
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

	public boolean HasPassword(String password) {
		return this.password.equals(password);
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("emailAddress", emailAddress)
			.toString();
	}
}
