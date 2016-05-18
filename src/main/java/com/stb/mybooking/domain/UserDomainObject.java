package com.stb.mybooking.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

@SuppressWarnings("serial")
@Document(collection = "users")
public class UserDomainObject implements UserDetails {
	
	@Id
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String password;
	private String confirmationToken;
	private boolean emailConfirmed;
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("emailAddress", emailAddress)
			.toString();
	}

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

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean authenticate(String password, PasswordEncoder passwordEncoder) {
		return emailConfirmed && passwordEncoder.matches(password, this.password);
	}
	
	public boolean isEmailConfirmed() {
		return emailConfirmed;
	}

	public void setToken(String token) {
		this.confirmationToken = Preconditions.checkNotNull(token);
	}

	public boolean confirmEmail(String confirmationToken) {
		Preconditions.checkNotNull(confirmationToken);
		emailConfirmed = this.confirmationToken.equals(confirmationToken);
		return emailConfirmed;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		password = passwordEncoder.encode(password);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getUsername() {
		return emailAddress;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return emailConfirmed;
	}
}
