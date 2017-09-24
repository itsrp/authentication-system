package com.rp.authenticationsystem.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rp.authenticationsystem.exception.BadRequestException;
import com.rp.authenticationsystem.exception.ConflictException;
import com.rp.authenticationsystem.exception.ForbiddenException;
import com.rp.authenticationsystem.exception.NotFoundException;
import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{
	
	private Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IMessageService messageService;
	
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
		LOGGER.info("Saving user with email: " + entity.getEmailId());
		userRepository.save(entity);
	}

	@Override
	@Transactional
	public void update(User entity) {
		LOGGER.info("Updating user with id: " + entity.getId());
		save(entity);
	}

	@Override
	@Transactional
	public void delete(User entity) {
		LOGGER.info("Deleting user with email: " + entity.getEmailId());
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
	public void verifyEmail(Long userId, String code) {
		User user = userRepository.findOne(userId);
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
	
}
