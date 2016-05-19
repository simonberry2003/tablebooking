package com.stb.mybooking.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class DemoAuthenticationProvider implements AuthenticationProvider {

    public DemoAuthenticationProvider() {
    }    
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}