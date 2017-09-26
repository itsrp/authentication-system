package com.rp.authenticationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rp.authenticationsystem.model.Token;
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

}
