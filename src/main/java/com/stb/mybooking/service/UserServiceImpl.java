package com.stb.mybooking.service;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.stb.mybooking.domain.UserDomainObject;

@Service
public class UserServiceImpl implements UserService {

	private final Map<ObjectId, UserDomainObject> users = new HashMap<>();
	private final Map<String, UserDomainObject> usersByEmail = new HashMap<>();
			
	@Override
	public UserDomainObject Get(String id) {
		return users.get(id);
	}

	@Override
	public UserDomainObject Register(UserDomainObject user) {
		ObjectId id = new ObjectId();
		user.setId(id);
		users.put(id, user);
		usersByEmail.put(user.getEmailAddress(), user);
		return user;
	}

	@Override
	public UserDomainObject Logon(String emailAddress, String password) {
		UserDomainObject user = usersByEmail.get(emailAddress);
		if (user != null && user.HasPassword(password)) {
			return user;
		}
		return null;
	}
}
