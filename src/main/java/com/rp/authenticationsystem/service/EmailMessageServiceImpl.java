package com.rp.authenticationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
		
		String body = String.format("To verify, please click on below link.\n%s/userId=%s&code=%s",
				getBasePath(),
				user.getId(),
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

}
