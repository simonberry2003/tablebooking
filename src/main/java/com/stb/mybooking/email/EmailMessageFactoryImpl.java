package com.stb.mybooking.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.stb.mybooking.domain.UserDomainObject;

@Component
public class EmailMessageFactoryImpl implements EmailMessageFactory {

	private final String sender;
	
	@Autowired
	public EmailMessageFactoryImpl(@Value("${email.sender}") String sender) {
		this.sender = Preconditions.checkNotNull(sender);
	}
	
	@Override
	public SimpleMailMessage createWelcomeEmail(UserDomainObject user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmailAddress());
		message.setFrom(sender);
		message.setSubject("Welcome to Tablebooking");
		String url = String.format("http://localhost:8081/users/confirm-email?emailAddress=%s&confirmationToken=%s", user.getEmailAddress(), user.getConfirmationToken());
		message.setText(String.format("Please click on the following link to activate your account.\n\n%s", url));
		return message;
	}
}
