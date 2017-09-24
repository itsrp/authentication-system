package com.rp.authenticationsystem.service;

import com.rp.authenticationsystem.model.User;

public interface IUserService extends ICrudService<User, Long> {

	void signUp(User user);

	void verifyEmail(Long userId, String code);

	void regenerateVerifyCode(String emailId);
}
