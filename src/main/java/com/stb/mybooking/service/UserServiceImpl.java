package com.stb.mybooking.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.stb.mybooking.domain.UserDomainObject;
import com.stb.mybooking.logging.LogFactory;
import com.stb.mybooking.repository.UserRepository;
import com.stb.mybooking.token.TokenGenerator;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private final Logger logger;
	private final UserRepository userRepository;
	private final TokenGenerator tokenGenerator;
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServiceImpl(
			LogFactory logFactory, 
			UserRepository userRepository, 
			TokenGenerator tokenGenerator,
			PasswordEncoder passwordEncoder) {
		
		this.logger = logFactory.create(this);
		this.userRepository = Preconditions.checkNotNull(userRepository);
		this.tokenGenerator = Preconditions.checkNotNull(tokenGenerator);
		this.passwordEncoder = Preconditions.checkNotNull(passwordEncoder);
	}
	
	@Override
	public UserDomainObject Register(UserDomainObject user) {

		user.setToken(tokenGenerator.Generate());
		user.encodePassword(passwordEncoder);
		user = userRepository.create(user);
		if (user != null) {
			// TODO: Send email
			logger.debug("Sending email to {}", user.getEmailAddress());
		}
		return user;
	}

	@Override
	public UserDomainObject Logon(String emailAddress, String password) {
		
		UserDomainObject user = userRepository.getByEmail(emailAddress);
		if (user != null && user.authenticate(password, passwordEncoder)) {
			return user;
		}
		return null;
	}

	@Override
	public boolean confirmEmail(String emailAddress, String token) {
		UserDomainObject user = userRepository.getByEmail(emailAddress);
		if (user != null && user.confirmEmail(token)) {
			user = userRepository.update(user);
			return true;
		}
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		
		UserDomainObject user = userRepository.getByEmail(emailAddress);
		if (user == null) {
			throw new UsernameNotFoundException("No such user");
		}
		return user;
	}
}
