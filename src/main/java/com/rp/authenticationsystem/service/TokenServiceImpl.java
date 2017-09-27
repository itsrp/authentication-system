package com.rp.authenticationsystem.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rp.authenticationsystem.exception.ForbiddenException;
import com.rp.authenticationsystem.model.Token;
import com.rp.authenticationsystem.model.User;
import com.rp.authenticationsystem.repository.ITokenRepository;

@Service
public class TokenServiceImpl implements ITokenService{
	
	@Autowired
	private ITokenRepository tokenRepository;

	@Override
	public Token get(Long id) {
		return tokenRepository.findOne(id);
	}

	@Override
	public void save(Token entity) {
		tokenRepository.save(entity);
		
	}

	@Override
	public void update(Token entity) {
		save(entity);
		
	}

	@Override
	public void delete(Token entity) {
		tokenRepository.delete(entity);
		
	}

	@Override
	public void expireToken(Long userId) {
		Token token = get(userId);
		token.setExpired(true);
		save(token);
	}

	@Override
	public String createToken(User user, boolean forceLogin) {
		if(isAlreadyLoggedIn(user)) {
			forceLogin(user, forceLogin);
		}
		
		String tokenCode = UUID.randomUUID().toString();
		Token token = new Token(tokenCode, user, LocalDateTime.now());
		save(token);
		return tokenCode;
	}

	private void forceLogin(User user, boolean forceLogin) {
		if(!forceLogin) {
			throw new ForbiddenException("User is already logged in.");
		}
		
		expireExistingToken(user);
	}

	private void expireExistingToken(User user) {
		Token token = tokenRepository.findByUserAndIsExpired(user, false);
		token.setExpired(true);
		save(token);
	}

	private boolean isAlreadyLoggedIn(User user) {
		Integer count = tokenRepository.countByUser(user);
		return count > 0 ? true : false;
	}

}
