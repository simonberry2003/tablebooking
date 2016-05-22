package com.stb.mybooking.email;

import org.springframework.mail.SimpleMailMessage;

import com.stb.mybooking.domain.UserDomainObject;

public interface EmailMessageFactory {
	SimpleMailMessage createWelcomeEmail(UserDomainObject user);
}
