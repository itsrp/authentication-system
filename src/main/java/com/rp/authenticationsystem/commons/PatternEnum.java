package com.rp.authenticationsystem.commons;

public enum PatternEnum {

	EMAIL(Constants.EMAIL_VALUE),
	MOBILE(Constants.MOBILE_VALUE);
	
	private String value;
	
	PatternEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static class Constants {
        public static final String EMAIL_VALUE = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        public static final String MOBILE_VALUE = "[0-9]{10}";
    }
}
