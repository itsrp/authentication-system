package com.rp.authenticationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rp.authenticationsystem.constants.EmailTemplate;
import com.rp.authenticationsystem.model.User;

@Service
public class EmailMessageServiceImpl implements IMessageService {
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Value("${app.name}")
	private String appName;
	
	@Value("${server.host}")
	private String host;
	
	@Value("${server.port}")
	private String port;
	
	@Override
	public void sendVerificationCode(User user) {
		
		String body = String.format("To verify, please click on below link.\n%s?emailId=%s&code=%s",
				getBasePath(),
				user.getEmailId(),
				user.getVerifyCode());
	    
	    SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(user.getEmailId()); 
	    message.setSubject(appName + " - Email Verification");
	    message.setText(body);
	    emailSender.send(message);
		
	}

	private String getBasePath() {
		return host + ":" + port + "/user/verification";
	}

	@Override
	public void sendNewPassword(User user, String newPassword) {
		String body = String.format("New password has been generated. Please login with below details and change the password."
				+ "\nUsername:%s\nPassword=%s",
				user.getEmailId(),
				newPassword);
	    
	    SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(user.getEmailId()); 
	    message.setSubject(appName + " - New password generated");
	    message.setText(body);
	    emailSender.send(message);
		
	}

	@Override
	public void sendNotification(String emailId, EmailTemplate emailTemplate) {
		SimpleMailMessage message = new SimpleMailMessage(); 
	    message.setTo(emailId); 
	    message.setSubject(appName + " - " + emailTemplate.getSubject());
	    message.setText(emailTemplate.getBody());
	    emailSender.send(message);
	}

}
