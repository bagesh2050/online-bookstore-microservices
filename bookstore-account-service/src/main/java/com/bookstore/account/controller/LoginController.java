package com.bookstore.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.account.exception.GenericBusinessException;
import com.bookstore.account.model.AuthTokenInfo;
import com.bookstore.account.model.Login;
import com.bookstore.account.service.LoginService;

@RestController
public class LoginController {
	@Autowired
	private LoginService loginService;

	@PostMapping(value = "/login", consumes = { "application/json;charset=UTF-8" })
	public AuthTokenInfo login(@RequestBody @Valid Login login) throws GenericBusinessException {
		// commenting part
		return loginService.getAuthorizationData(login.getUsername(), login.getPassword());
	}
}
