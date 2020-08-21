package com.bookstore.inventory.security;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
@Order(2)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
	RSAPublicKey key;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests().anyRequest().authenticated().and().oauth2ResourceServer(
				oauth2ResourceServer -> oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder())));
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.key).build();
	}
}