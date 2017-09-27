package com.rp.authenticationsystem.service;

import com.rp.authenticationsystem.model.User;

public interface IUserService extends ICrudService<User, Long> {

	void signUp(User user);

	void verifyEmail(String emailId, String code);

	void regenerateVerifyCode(String emailId);

	void forgotPassword(String emailId);

	void changePassword(String emailId, String oldPassword, String newPassword);

	String login(String emailId, String password, boolean forceLogin);
}
