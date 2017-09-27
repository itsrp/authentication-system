package com.rp.authenticationsystem.service;

import com.rp.authenticationsystem.constants.EmailTemplate;
import com.rp.authenticationsystem.model.User;

public interface IMessageService {

	void sendVerificationCode(User user);

	void sendNewPassword(User user, String newPassword);

	void sendNotification(String emailId, EmailTemplate emailTemplate);
}
