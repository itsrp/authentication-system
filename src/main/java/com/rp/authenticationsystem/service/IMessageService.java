package com.rp.authenticationsystem.service;

import com.rp.authenticationsystem.model.User;

public interface IMessageService {

	void sendVerificationCode(User user);
}
