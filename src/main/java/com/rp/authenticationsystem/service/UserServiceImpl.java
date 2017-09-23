package com.rp.authenticationsystem.service;

import java.util.UUID;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{
	
	private Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IMessageService messageService;

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
		LOGGER.info("Updating user with email: " + entity.getEmailId());
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
	
	@Autowired
	public void setUserRepository(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
}
