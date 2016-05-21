package com.stb.mybooking.validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

@Component
public class EmailValidatorImpl implements EmailValidator {

	@Override
	public boolean validate(String emailAddress) {
	   try {
	      InternetAddress emailAddr = new InternetAddress(emailAddress);
	      emailAddr.validate();
	      return true;
	   } catch (AddressException ex) {
	   }
	   return false;
	}
}
