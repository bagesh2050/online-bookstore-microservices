package com.bookstore.account.exception;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.bookstore.account.utils.JsonUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiErrorResponse {
	protected int code;
	protected String message;
	protected String description;

	public ApiErrorResponse() {
		super();
	}

	public ApiErrorResponse(ReturnCode returnCode, String description) {
		this.code = returnCode.getCode();
		this.description = description;
	}

	public ApiErrorResponse(ReturnCode returnCode, String message, String description) {
		this.code = returnCode.getCode();
		this.message = message;
		this.description = description;
	}

	public ApiErrorResponse(String jsonBody) throws IOException {
		ApiErrorResponse extracted = JsonUtils.buildApiError(jsonBody);
		this.code = extracted.getCode();
		this.message = extracted.getMessage();
		this.description = extracted.getDescription();
	}

	@Override
	public String toString() {
		return String.format("ApiErrorResponse [code=%s, message=%s, description=%s]", code, message, description);
	}

	@JsonIgnore
	public HttpStatus getHttpStatus() {
		return getReturnCode().map(ReturnCode::getHttpStatus).orElse(null);
	}

	@JsonIgnore
	public Optional<ReturnCode> getReturnCode() {
		return Optional.ofNullable(ReturnCode.findByCode(code));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}
}
