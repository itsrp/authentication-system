package com.rp.authenticationsystem.service;

import com.rp.authenticationsystem.model.Token;
import com.rp.authenticationsystem.model.User;

public interface ITokenService extends ICrudService<Token, Long> {

	void expireToken(Long id);

	String createToken(User user, boolean forceLogin);

}
