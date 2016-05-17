package com.stb.mybooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.stb.mybooking.domain.UserDomainObject;
import com.stb.mybooking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = Preconditions.checkNotNull(userRepository);
	}
	
	@Override
	public UserDomainObject Get(String id) {
		return null;
	}

	@Override
	public UserDomainObject Register(UserDomainObject user) {
		return userRepository.create(user);
	}

	@Override
	public UserDomainObject Logon(String emailAddress, String password) {
		UserDomainObject user = userRepository.getByEmail(emailAddress);
		if (user != null && user.HasPassword(password)) {
			return user;
		}
		return null;
	}
}
