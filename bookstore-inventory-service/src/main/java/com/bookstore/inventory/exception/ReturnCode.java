package com.bookstore.inventory.exception;

import org.springframework.http.HttpStatus;

/*
 * Enumeration of response error codes
 * 
 * */
public enum ReturnCode {
	INTERNAL_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR),

	/**
	 * {@code It defines wrong type of value for example string in place of integer}.
	 */
	INVALID_TYPE_PARAMETER_VALUE(20, HttpStatus.BAD_REQUEST),
	MISSING_BODY(21, HttpStatus.BAD_REQUEST),
	MISSING_BODY_FIELD(23, HttpStatus.BAD_REQUEST),
	INVALID_BODY_FIELD(24, HttpStatus.BAD_REQUEST),
	MISSING_HEADER(25, HttpStatus.BAD_REQUEST),
	INVALID_HEADER(26, HttpStatus.BAD_REQUEST),
	MISSING_QUERYSTRING_PARAMETER(27, HttpStatus.BAD_REQUEST),

	/**
	 * {@code It defines which parameters cann't be together in a request like Subscriber and agentCode}.
	 */
	INVALID_PARAMETER_COMBINATION(28, HttpStatus.BAD_REQUEST),

	/**
	 * {@code It verify the value of parameter like UserType can have value from (SUBSCRIBER,CHANNEL,PSEUDO)}.
	 */
	INVALID_PARAMETER_VALUE(29, HttpStatus.BAD_REQUEST),
	INVALID_TOKEN(30, HttpStatus.UNAUTHORIZED),
	FORBIDDEN(50, HttpStatus.FORBIDDEN),
	RESOURCE_NOT_FOUND(60, HttpStatus.NOT_FOUND),
	NOT_ACCEPTABLE(62, HttpStatus.NOT_ACCEPTABLE),
	REQUEST_ENTITY_TOO_LARGE(66, HttpStatus.PAYLOAD_TOO_LARGE),
	UNSUPPORTED_MEDIA_TYPE(68, HttpStatus.UNSUPPORTED_MEDIA_TYPE),

	/**
	 * Generic messages
	 */

	SUCCESS_MESSAGE(200, HttpStatus.OK),

	/**
	 * generic technical errors
	 */
	INTERNAL_ERROR_BAD_REQUEST(3001, HttpStatus.BAD_REQUEST),
	INTERNAL_ERROR_DB(3002, HttpStatus.INTERNAL_SERVER_ERROR),
	ACCESS_DENIED(3004, HttpStatus.UNAUTHORIZED),
	ACCESS_TOKEN_EXPIRED(3005, HttpStatus.UNAUTHORIZED),
	DATABASE_VALUE_DB(3006, HttpStatus.BAD_REQUEST),
	TIMEZONE_NOT_MACTHED(3007, HttpStatus.BAD_REQUEST),
	INVALID_DATES(3008, HttpStatus.BAD_REQUEST),
	DUPLICATE_RECORD_DB(3009, HttpStatus.BAD_REQUEST),
	INVALID_PARAMETER(3010, HttpStatus.BAD_REQUEST);

	private final int code;
	private final HttpStatus httpStatus;

	ReturnCode(int code, HttpStatus httpStatus) {
		this.code = code;
		this.httpStatus = httpStatus;
	}

	public static ReturnCode findByCode(int code) {
		for (ReturnCode returnCode : values()) {
			if (returnCode.getCode() == code) {
				return returnCode;
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
