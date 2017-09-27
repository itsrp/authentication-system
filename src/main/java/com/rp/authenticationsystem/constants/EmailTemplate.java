package com.rp.authenticationsystem.constants;

public enum EmailTemplate {

	PASSWORD_CHANGED("Password Change", "Password changed successfully");
	
	
	private EmailTemplate(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}
	
	private String subject;
	
	private String body;

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}
	
	
}
