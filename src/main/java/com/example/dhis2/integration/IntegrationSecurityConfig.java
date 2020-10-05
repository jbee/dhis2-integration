package com.example.dhis2.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class IntegrationSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value( "${service.users}" )
	private String serviceUsers;

	@Override
	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> users= new ArrayList<>();
		for (String user : serviceUsers.split(",")) {
			String[] usernamePassword = user.split(":");
			users.add(User.withDefaultPasswordEncoder()
					.username(usernamePassword[0])
					.password(usernamePassword[1])
					.roles("API").build());
		}
		return new InMemoryUserDetailsManager(users);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests().anyRequest().authenticated()
				.and().httpBasic();
	}

}
