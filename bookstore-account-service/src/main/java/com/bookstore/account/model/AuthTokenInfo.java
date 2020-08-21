package com.bookstore.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthTokenInfo {
	@JsonProperty(value = "accesstoken")
	private String accessToken;
	@JsonProperty(value = "tokentype")
	private String tokenType;
	@JsonProperty(value = "refreshtoken")
	private String refreshToken;
	@JsonProperty(value = "expiresin")
	private int expiresIn;
	@JsonProperty(value = "scope")
	private String scope;
}
