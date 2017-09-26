package com.rp.authenticationsystem.encryption;

import java.nio.charset.Charset;
import java.util.Base64;

import com.rp.authenticationsystem.exception.BadRequestException;

public class HashingUtil {
	
	public static String[] extractAuthHeader(String authorization) {
		if (authorization != null && authorization.startsWith("Basic")) {
	        // Authorization: Basic base64credentials
	        String base64Credentials = authorization.substring("Basic".length()).trim();
	        String credentials = new String(Base64.getDecoder().decode(base64Credentials),
	                Charset.forName("UTF-8"));
	        // credentials = username:password
	        return credentials.split(":",2);
		}
		throw new BadRequestException("Authentication header is wrong.");
	}
}
