package com.stb.mybooking.service;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.stb.mybooking.domain.UserDomainObject;
import com.stb.mybooking.email.EmailMessageFactory;
import com.stb.mybooking.job.JobScheduler;
import com.stb.mybooking.job.email.SendEmailJob;
import com.stb.mybooking.logging.LogFactory;
import com.stb.mybooking.repository.UserRepository;
import com.stb.mybooking.token.TokenGenerator;
import com.stb.mybooking.validator.EmailValidator;

@Service
public class UserServiceImpl implements UserService {

	private final Logger logger;
	private final UserRepository userRepository;
	private final TokenGenerator tokenGenerator;
	private final PasswordEncoder passwordEncoder;
	private final EmailValidator emailValidator;
	private final JobScheduler jobScheduler;
	private final JavaMailSender javaMailSender;
	private final EmailMessageFactory emailMessageFactory;
	
	@Autowired
	public UserServiceImpl(
		LogFactory logFactory, 
		UserRepository userRepository, 
		TokenGenerator tokenGenerator,
		PasswordEncoder passwordEncoder,
		EmailValidator emailValidator,
		JobScheduler jobScheduler,
		JavaMailSender javaMailSender,
		EmailMessageFactory emailMessageFactory) {
		
		this.logger = logFactory.create(this);
		this.userRepository = Preconditions.checkNotNull(userRepository);
		this.tokenGenerator = Preconditions.checkNotNull(tokenGenerator);
		this.passwordEncoder = Preconditions.checkNotNull(passwordEncoder);
		this.emailValidator = Preconditions.checkNotNull(emailValidator);
		this.jobScheduler = Preconditions.checkNotNull(jobScheduler);
		this.javaMailSender = Preconditions.checkNotNull(javaMailSender);
		this.emailMessageFactory = Preconditions.checkNotNull(emailMessageFactory);
	}
	
	@Override
	public UserDomainObject Register(UserDomainObject user) {

		List<String> errors = user.validate(emailValidator);
		if (errors.size() > 0) {
			throw new RuntimeException(errors.toString());
		}
			
		user.setToken(tokenGenerator.Generate());
		user.encodePassword(passwordEncoder);
		user = userRepository.create(user);
		if (user != null) {
			logger.debug("Sending email to {}", user.getEmailAddress());
			SendEmailJob job = new SendEmailJob();
			job.setJavaMailSender(javaMailSender);
			SimpleMailMessage message = emailMessageFactory.createWelcomeEmail(user);
			job.setMessage(message);
			jobScheduler.enqueue(job);
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
}
