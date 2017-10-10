package com.rp.authenticationsystem.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rp.authenticationsystem.encryption.HashingUtil;
import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.response.Response;
import com.rp.authenticationsystem.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserController.class.getName());

	private IUserService userService;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Response> signUp(@Valid @RequestBody User user) {
		LOGGER.info("SignUp request for user:" + user.getEmailId());
		userService.signUp(user);
		Response response = new Response(201, "Account created.");
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public ResponseEntity<Response> verifyEmail(@RequestParam String emailId, @RequestParam String code) {
		LOGGER.info("Email verification request for emailId:" + emailId);
		userService.verifyEmail(emailId, code);
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
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<Response> login(@RequestHeader String authorization, @RequestHeader(required = false) boolean forceLogin) {
		String[] extractedAuthHeader = HashingUtil.extractAuthHeader(authorization);
		LOGGER.info("Login request for username:" + extractedAuthHeader[0]);
		String token = userService.login(extractedAuthHeader[0], extractedAuthHeader[1], forceLogin);
		Response response = new Response(200, "LoggedIn successfully.");
		response.setData(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId){
		LOGGER.info("Forgot password request for email:" + emailId);
		userService.forgotPassword(emailId);
		Response response = new Response(200, "Verify link sent to registered email.");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ResponseEntity<Response> changePassword(@RequestHeader String authorization, @RequestHeader String newPassword){
		String[] extractedAuthHeader = HashingUtil.extractAuthHeader(authorization);
		LOGGER.info("Change password request for username:" + extractedAuthHeader[0]);
		userService.changePassword(extractedAuthHeader[0], extractedAuthHeader[1], newPassword);
		Response response = new Response(200, "Password changed successfully.");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@Autowired
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
