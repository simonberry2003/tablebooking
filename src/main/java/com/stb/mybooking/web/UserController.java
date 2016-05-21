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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.stb.mybooking.domain.UserDomainObject;
import com.stb.mybooking.service.UserService;
import com.stb.mybooking.web.model.RegisterUserRequestWebObject;
import com.stb.mybooking.web.model.UserLogonRequestWebObject;
import com.stb.mybooking.web.model.UserWebObject;

@RestController
@RequestMapping("users")
public class UserController {

	private final UserService userService;
	private final ModelMapper mapper;
	
	@Autowired
	public UserController(UserService userService, ModelMapper mapper) {
		this.userService = Preconditions.checkNotNull(userService);
		this.mapper = Preconditions.checkNotNull(mapper);
	}
	
    @ApiOperation(value = "register", nickname = "register")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "registerUserRequest", value = "the register user request", required = true, dataType = "RegisterUserRequestWebObject", paramType = "body")
    })
    @RequestMapping(method = RequestMethod.POST, path="/register", produces = "application/json")
    public ResponseEntity<UserWebObject> register(@RequestBody RegisterUserRequestWebObject registerUserRequest) {
    	
    	UserDomainObject domainUser = mapper.map(registerUserRequest, UserDomainObject.class);
    	domainUser = userService.Register(domainUser);
    	if (domainUser == null) {
    		return new ResponseEntity<>(HttpStatus.CONFLICT);
    	}
    	UserWebObject webUser = mapper.map(domainUser, UserWebObject.class);
		return new ResponseEntity<>(webUser, HttpStatus.OK);
    }

    @ApiOperation(value = "logon", nickname = "logon")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "logonRequest", value = "the logon request", required = true, dataType = "UserLogonRequestWebObject", paramType = "body")
    })
    @RequestMapping(method = RequestMethod.POST, path="/logon", produces = "application/json")
    public ResponseEntity<UserWebObject> logon(@RequestBody UserLogonRequestWebObject logonRequest) {
    	
    	UserDomainObject domainUser = userService.Logon(logonRequest.getEmailAddress(), logonRequest.getPassword());
    	if (domainUser == null) {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	UserWebObject webUser = mapper.map(domainUser, UserWebObject.class);
		return new ResponseEntity<>(webUser, HttpStatus.OK);
    }

    @ApiOperation(value = "confirmEmail", nickname = "confirmEmail")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "emailAddress", value = "a user's email", required = true, dataType = "String", paramType = "query"),
        @ApiImplicitParam(name = "confirmationToken", value = "the token allocated during registration", required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(method = RequestMethod.GET, path="/confirm-email")
    public ResponseEntity<Void> confirmEmail(@RequestParam String emailAddress, @RequestParam String confirmationToken) {
    	
    	if (userService.confirmEmail(emailAddress, confirmationToken)) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
