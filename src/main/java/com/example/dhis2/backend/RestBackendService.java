package com.example.dhis2.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestBackendService implements BackendService {

	@Autowired
	private BackendConfig server;
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public static RestTemplate restTemplate(RestTemplateBuilder builder, BackendConfig server) {
		return builder
				.basicAuthentication(server.getUsername(), server.getPassword())
				.build();
	}

	@Override
	public DataElements loadDataElements() {
		String uri = server.getUrl() +  server.getDataElements();
		return restTemplate.getForObject(uri, DataElements.class);
	}

	@Override
	public DataElementGroups loadDataElementGroups() {
		String uri = server.getUrl() +  server.getDataElementGroups();
		return restTemplate.getForObject(uri, DataElementGroups.class);
	}
}
