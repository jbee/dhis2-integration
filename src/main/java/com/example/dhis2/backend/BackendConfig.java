package com.example.dhis2.backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "dhis2")
@Configuration("backend-server")
public class BackendConfig {

	private String url;
	private String username;
	private String password;
	private String dataElements;
	private String dataElementGroups;

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getDataElements() {
		return dataElements;
	}

	public String getDataElementGroups() {
		return dataElementGroups;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDataElements(String dataElements) {
		this.dataElements = dataElements;
	}

	public void setDataElementGroups(String dataElementGroups) {
		this.dataElementGroups = dataElementGroups;
	}
}
