package com.stb.mybooking;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.authorizeRequests()
        	.anyRequest()
        	.permitAll();
		//http.addFilterBefore(new DemoAuthenticationFilter(), BasicAuthenticationFilter.class);		
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(demoAuthenticationProvider);   
    }	
}
