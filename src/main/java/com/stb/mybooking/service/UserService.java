package com.stb.mybooking.service;

import com.stb.mybooking.domain.UserDomainObject;

public interface UserService {

	UserDomainObject Register(UserDomainObject user);
	UserDomainObject Logon(String emailAddress, String password);
	boolean confirmEmail(String emailAddress, String token);
}
