package com.stb.mybooking.repository;

import com.stb.mybooking.domain.UserDomainObject;

public interface UserRepository {
	UserDomainObject create(UserDomainObject user);
	UserDomainObject getByEmail(String emailAddress);
	UserDomainObject update(UserDomainObject user);
}
