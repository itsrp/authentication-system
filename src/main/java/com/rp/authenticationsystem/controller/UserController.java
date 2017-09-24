package com.rp.authenticationsystem.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.response.Response;
import com.rp.authenticationsystem.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private Logger LOGGER = Logger.getLogger(UserController.class.getName());

	private IUserService userService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Response> signUp(@Valid @RequestBody User user) {
		LOGGER.info("SignUp request for user:" + user.getEmailId());
		userService.signUp(user);
		Response response = new Response(201, "Account created.");
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyEmail(@RequestParam Long userId, @RequestParam String code) {
		LOGGER.info("Email verification request for user:" + userId);
		userService.verifyEmail(userId, code);
		Response response = new Response(200, "Email verified.");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/regenerateVerifyCode", method = RequestMethod.GET)
	public ResponseEntity<Response> regenerateVerificationCode(@RequestParam String emailId) {
		LOGGER.info("Regenerate Email verification code request for email:" + emailId);
		userService.regenerateVerifyCode(emailId);
		Response response = new Response(200, "Verification code sent to emailId. Please verify.");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
