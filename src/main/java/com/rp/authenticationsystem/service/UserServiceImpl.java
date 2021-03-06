package com.rp.authenticationsystem.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.rp.authenticationsystem.constants.EmailTemplate;
import com.rp.authenticationsystem.controller.UserController;
import com.rp.authenticationsystem.exception.BadRequestException;
import com.rp.authenticationsystem.exception.ConflictException;
import com.rp.authenticationsystem.exception.ForbiddenException;
import com.rp.authenticationsystem.exception.NotAuthorizedException;
import com.rp.authenticationsystem.exception.NotFoundException;
import com.rp.authenticationsystem.model.Token;
import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{
	
	private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IMessageService messageService;
	
	@Autowired
	private ITokenService tokenService;
	
	@Value("${auth.app.expire.email.link.time}")
	private String expireTimeoutInterval;
	
	@Override
	@Transactional
	public User get(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	@Transactional
	public void save(User entity) {
		LOGGER.debug("Saving user with email: " + entity.getEmailId());
		entity.setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt(12)));
		userRepository.save(entity);
	}

	@Override
	@Transactional
	public void update(User entity) {
		LOGGER.debug("Updating user with id: " + entity.getId());
		save(entity);
	}

	@Override
	@Transactional
	public void delete(User entity) {
		LOGGER.debug("Deleting user with email: " + entity.getEmailId());
		userRepository.delete(entity);
	}

	@Override
	@Transactional
	public void signUp(User user) {
		user.setVerifyCode(UUID.randomUUID().toString());
		save(user);
		messageService.sendVerificationCode(user);
	}

	@Override
	@Transactional
	public void verifyEmail(String emailId, String code) {
		LOGGER.debug("verify email: " + emailId);
		User user = userRepository.findByEmailId(emailId);
		if(user == null) {
			throw new NotFoundException("User not found. Please contact administrator.");
		}
		
		isAlreadyVerified(user);
		isUserExpired(user);
		
		validateCode(code, user);
		
	}

	private void validateCode(String code, User user) {
		if(user.getVerifyCode().equals(code)) {
			user.setVerified(true);
			save(user);
			LOGGER.info("User email verified successfully for user: " + user.getId());
		} else {
			throw new BadRequestException("Invalid email verification code.");
		}
	}

	private void isUserExpired(User user) {
		Long milliDiff = ChronoUnit.MILLIS.between(user.getLastModifiedDate(), LocalDateTime.now());
		if(Long.valueOf(expireTimeoutInterval) < milliDiff) {
			throw new ForbiddenException("The link is expired. "
					+ "To verify email, Please click here to re-generate email verification link.");//TODO re-generate code login to implement
		}
	}

	private void isAlreadyVerified(User user) {
		if(user.isVerified()) {
			throw new ConflictException("Email is already verified.");
		}
	}
	
	@Autowired
	public void setUserRepository(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void regenerateVerifyCode(String emailId) {
		User user = userRepository.findByEmailId(emailId);
		if(user == null) {
			throw new NotFoundException("User not found. Please contact administrator.");
		}
		isAlreadyVerified(user);
		signUp(user);
	}

	@Override
	public String login(String emailId, String password, boolean forceLogin) {
		User user = getUser(emailId);
		if(!BCrypt.checkpw(password, user.getPassword())) {
			throw new NotAuthorizedException("Username or Password is wrong.");
		}
		
		return tokenService.createToken(user, forceLogin);
	}

	private String createToken(User user) {
		Token token = new Token();
		token.setLoggedInDateTIme(LocalDateTime.now());
		token.setUser(user);
		String tokenCode = UUID.randomUUID().toString();
		token.setCode(tokenCode);
		tokenService.save(token);
		return tokenCode;
	}

	@Override
	public void forgotPassword(String emailId) {
		User user = getUser(emailId);
		String newPassword = UUID.randomUUID().toString();
		updatePassword(user, newPassword);
		messageService.sendNewPassword(user, newPassword);
	}

	@Override
	public void changePassword(String emailId, String oldPassword, String newPassword) {
		User user = getUser(emailId);
		if(!BCrypt.checkpw(oldPassword, user.getPassword())) {
			throw new NotAuthorizedException("Username or Password is wrong.");
		}
		updatePassword(user, newPassword);
		messageService.sendNotification(user.getEmailId(), EmailTemplate.PASSWORD_CHANGED);
	}
	
	private void updatePassword(User user, String newPassword) {
		user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt(12)));
		userRepository.save(user);
		tokenService.expireToken(user.getId());
	}

	private User getUser(String emailId) {
		User user = userRepository.findByEmailId(emailId);
		if(user == null) {
			throw new NotFoundException("User not found.");
		}
		if(user.isDeleted()) {
			throw new NotFoundException("User is deleted.");
		}
		if(!user.isVerified()) {
			throw new ForbiddenException("User is not verified. Please verify.");
		}
		if(user.isBlocked()) {
			throw new ForbiddenException("User is blocked. Please contact administrator.");
		}
		return user;
	}

	
	
}
