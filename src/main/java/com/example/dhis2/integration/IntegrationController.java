package com.example.dhis2.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@RestController
@Validated
public class IntegrationController {

	@Autowired
	private IntegrationService service;

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	@GetMapping(value = "/api/dataElements", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
	@ResponseBody
	public ResponseEntity<DataElement> getDataElement(@RequestParam String id) {
		return getDataElementResponse(id);
	}

	@GetMapping(value = "/api/dataElements.json",	produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<DataElement> getDataElementJson(@RequestParam String id) {
		return getDataElementResponse(id);
	}

	@GetMapping(value = "/api/dataElements.xml",	produces = APPLICATION_XML_VALUE)
	@ResponseBody
	public ResponseEntity<DataElement> getDataElementXml(@RequestParam String id) {
		return getDataElementResponse(id);
	}

	private ResponseEntity<DataElement> getDataElementResponse(String id) {
		DataElement res = service.getDataElementById(id);
		return res == null
				? ResponseEntity.notFound().build()
				: ResponseEntity.ok(res);
	}

	@GetMapping(value = "/api/dataElementGroups", produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
	public @ResponseBody ResponseEntity<DataElementGroup> getDataElementGroup(@RequestParam String id) {
		return getDataElementGroupResponse(id);
	}

	@GetMapping(value = "/api/dataElementGroups.json", produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<DataElementGroup> getDataElementGroupJson(@RequestParam String id) {
		return getDataElementGroupResponse(id);
	}

	@GetMapping(value = "/api/dataElementGroups.xml", produces = APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEntity<DataElementGroup> getDataElementGroupXml(@RequestParam String id) {
		return getDataElementGroupResponse(id);
	}

	private ResponseEntity<DataElementGroup> getDataElementGroupResponse(String id) {
		DataElementGroup res = service.getDataElementGroupById(id);
		return res == null
				? ResponseEntity.notFound().build()
				: ResponseEntity.ok(res);
	}
}
