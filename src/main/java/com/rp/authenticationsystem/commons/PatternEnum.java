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
        public static final String EMAIL_VALUE = "^([_a-zA-Z0-9-]+(\\\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\\\.[a-zA-Z0-9-]+)*(\\\\.[a-zA-Z]{1,6}))?$";
        public static final String MOBILE_VALUE = "[0-9]{10}";
    }
}
