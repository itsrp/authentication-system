package com.rp.authenticationsystem.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.response.Response;
import com.rp.authenticationsystem.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private Logger LOGGER = Logger.getLogger(UserController.class.getName());

	private IUserService userService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<Response> signUp(@Valid User user) {
		LOGGER.info("SignUp request for user:" + user.getEmailId());
		userService.signUp(user);
		Response response = new Response(201, "Account created.");
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
