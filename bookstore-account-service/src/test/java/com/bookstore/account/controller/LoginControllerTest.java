package com.bookstore.account.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bookstore.account.model.AuthTokenInfo;
import com.bookstore.account.service.LoginService;
import com.bookstore.account.util.TestUtility;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	@MockBean
	private LoginService loginService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLogin() throws Exception {

		AuthTokenInfo expectedTokenInfo = prepareExpectedResponse();
		final String expectedResponse = TestUtility.mapObjectToJson(expectedTokenInfo);

		Mockito.when(loginService.getAuthorizationData(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(expectedTokenInfo);

		final RequestBuilder builder = MockMvcRequestBuilders.post("/login").param("username", "admin")
				.param("password", "123456").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		final MvcResult mvcResult = mockMvc.perform(builder).andReturn();
		final MockHttpServletResponse httpResponse = mvcResult.getResponse();

		final String resultJson = httpResponse.getContentAsString();
		assertThat(resultJson).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), httpResponse.getStatus());
	}

	private AuthTokenInfo prepareExpectedResponse() throws JsonProcessingException {
		final AuthTokenInfo authToken = new AuthTokenInfo();
		authToken.setAccessToken(
				"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIHByaXZpbGFnZXMiOnsiYXBwbGljYXRpb25JZCI6IkJPT0tTVE9SRSIsInZlcnNpb24iOiJ2MSIsImFwaURldGFpbHMiOlt7ImFwaSI6IkFETUlOIiwidmVyc2lvbiI6InYxIiwicmVzcG9uc2VTdHJ1Y3R1cmUiOlt7InZhcmlhYmxlIjoiQUxMIn1dfV19LCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbInJlYWQiXSwiZXhwIjoxNTkzNjgxMDkzLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6IjEzNmQ1ZTdiLWVkNGMtNGI0ZC05YWJjLTRlOGU1Y2IzNzcwNCIsImNsaWVudF9pZCI6IkJPT0tTVE9SRV9WMSJ9.HxyiMqoZGlNk4T7MRauue4J82P9duob87YWVkAvMwLvzK8TYqd6qtGxzZbebD6b7luJjXTOdawnrXmRySlQWgtmgkvhA_KtHno0z1WySLVzb5mbGJfbHVNm-KWkX5eXMEmU7ULaOLx4Qf9xGHFbfGz84xjr7pI5vTdgUA5ZzjpKzWhiN2qdu2bn6URFisY9slIDU3N6_Ch83NTgKP6mwypzG-WXsJYU-iBeq8Wzc62dO5lZUYNT7MlVacAJPEugqj3mcgWTPYfa6jyL-DP_IoB1qKkeO3s9VELrIzeqYEWHlzCyk5Wc_pH58S_8RsWd9D7pftuqYSvmhJa3UzOlPDw");
		authToken.setRefreshToken(null);
		authToken.setTokenType("bearer");
		authToken.setExpiresIn(3599);
		authToken.setScope("read");
		return authToken;
	}
}
