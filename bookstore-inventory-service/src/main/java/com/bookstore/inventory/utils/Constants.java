package com.bookstore.inventory.utils;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class must not be initialized.");
	}
	
	public static final String RESPONSE_MSG_ACCESS_TOKEN_EXPIRED = "Access token expired";
	public static final String RESPONSE_MSG_INVALID_TOKEN = "token is not valid";
	public static final String RESPONSE_MSG_DB_EXCEPTION = "Internal database error";
	public static final String RESPONSE_MSG_INVALID_REQUEST_BODY = "missing body or bad content";

}
