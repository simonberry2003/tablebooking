package com.stb.mybooking.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.stb.mybooking.domain.UserDomainObject;
import com.stb.mybooking.service.UserService;
import com.stb.mybooking.web.model.RegisterUserRequestWebObject;
import com.stb.mybooking.web.model.UserLogonRequestWebObject;
import com.stb.mybooking.web.model.UserWebObject;

@RestController
public class UserController {

	private final UserService userService;
	private final ModelMapper mapper;
	
	@Autowired
	public UserController(UserService userService, ModelMapper mapper) {
		this.userService = Preconditions.checkNotNull(userService);
		this.mapper = Preconditions.checkNotNull(mapper);
	}
	
    @ApiOperation(value = "registerUser", nickname = "registerUser")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "user", value = "the user to register", required = true, dataType = "RegisterUserRequestWebObject", paramType = "body")
    })
    @RequestMapping(method = RequestMethod.POST, path="/register-user", produces = "application/json")
    public ResponseEntity<UserWebObject> registerUser(@RequestBody RegisterUserRequestWebObject user) {
    	UserDomainObject domainUser = mapper.map(user, UserDomainObject.class);
    	domainUser = userService.Register(domainUser);
    	UserWebObject webUser = mapper.map(domainUser, UserWebObject.class);
		return new ResponseEntity<UserWebObject>(webUser, HttpStatus.OK);
    }

    @ApiOperation(value = "logonUser", nickname = "logonUser")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "logonRequest", value = "the logon request", required = true, dataType = "UserLogonRequestWebObject", paramType = "body")
    })
    @RequestMapping(method = RequestMethod.POST, path="/logon-user", produces = "application/json")
    public ResponseEntity<UserWebObject> logonUser(@RequestBody UserLogonRequestWebObject logonRequest) {
    	UserDomainObject domainUser = userService.Logon(logonRequest.getEmailAddress(), logonRequest.getPassword());
    	if (domainUser == null)
    	{
    		return new ResponseEntity<UserWebObject>(HttpStatus.UNAUTHORIZED);
    	}
    	UserWebObject webUser = mapper.map(domainUser, UserWebObject.class);
		return new ResponseEntity<UserWebObject>(webUser, HttpStatus.OK);
    }
}
