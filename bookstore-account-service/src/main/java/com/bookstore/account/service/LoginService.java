package com.bookstore.account.service;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.bookstore.account.exception.GenericBusinessException;
import com.bookstore.account.exception.ReturnCode;
import com.bookstore.account.model.AuthTokenInfo;

@Service
public class LoginService {
	private Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Value("${auth.server.clientId}")
	private String clientId;

	@Value("${auth.server.host}")
	private String host;

	@Value("${auth.server.port}")
	private String port;

	@Value("${auth.server.context-path}")
	private String contextPath;

	public AuthTokenInfo getAuthorizationData(String username, String password) throws GenericBusinessException {
		final String AUTH_SERVER_URI = "http://".concat(host).concat(":").concat(port).concat("/").concat(contextPath)
				.concat("/auth");

		MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
		parametersMap.add("clientId", clientId);
		parametersMap.add("login", username);
		parametersMap.add("password", password);

		final RestTemplate restTemplate = new RestTemplate();

		AuthTokenInfo tokenInfo = null;
		try {
			Object response = restTemplate.postForObject(AUTH_SERVER_URI, parametersMap, Object.class);

			final LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response;
			tokenInfo = new AuthTokenInfo();
			tokenInfo.setAccessToken((String) map.get("accesstoken"));
			tokenInfo.setTokenType((String) map.get("tokentype"));
			tokenInfo.setRefreshToken((String) map.get("refreshtoken"));
			tokenInfo.setExpiresIn((int) map.get("expiresin"));
			tokenInfo.setScope((String) map.get("scope"));
		} catch (Exception e) {
			throw new GenericBusinessException(ReturnCode.INTERNAL_ERROR, "Unable to get token" + e.getMessage());
		}
		return tokenInfo;
	}
}
