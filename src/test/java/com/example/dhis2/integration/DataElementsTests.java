package com.example.dhis2.integration;

import com.example.dhis2.backend.BackendService;
import com.example.dhis2.backend.DataElementGroups;
import com.example.dhis2.backend.DataElements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests the {@link IntegrationController#getDataElement(String)} methods
 */
@WebMvcTest(IntegrationController.class)
class DataElementsTests {

	@TestConfiguration
	static class ExampleTestConfiguration {

		@Bean
		public IntegrationService integrationService() {
			return new IntegrationServiceImpl(backendService());
		}

		@Bean
		public BackendService backendService() {
			BackendService backend = mock(BackendService.class);
			DataElements elements = new DataElements();
			when(backend.loadDataElements()).thenReturn(elements);
			elements.setDataElements(new ArrayList<>());
			DataElements.DataElement element = new DataElements.DataElement();
			element.setId("testid");
			element.setDisplayName("testname");
			element.setDataElementGroups(asList(
					new DataElements.DataElementGroup("a"),
					new DataElements.DataElementGroup("b")
					));
			elements.getDataElements().add(element);
			DataElementGroups groups = new DataElementGroups();
			groups.setDataElementGroups(new ArrayList<>());
			when(backend.loadDataElementGroups()).thenReturn(groups);
			return backend;
		}
	}

	@Autowired
	private WebApplicationContext context;

	private  MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	public void dataElementRequiresUserAndPassword() throws Exception {
		assertResponseStatus("doesNotExist", status().isUnauthorized());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementRequiresIdParameter() throws Exception {
		assertResponseStatus(null, status().isBadRequest());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementReturnsNotFoundForNonExistingEntries() throws Exception {
		assertResponseStatus("doesNotExist", status().isNotFound());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementReturnsOkForExistingEntries() throws Exception {
		assertResponseStatus("testid", status().isOk());
		assertEquals("{\"id\":\"testid\",\"name\":\"testname\",\"groups\":[\"a\",\"b\"]}",
				getRequest("testid").andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementJsonExtensionIsAccepted() throws Exception {
		assertEquals("{\"id\":\"testid\",\"name\":\"testname\",\"groups\":[\"a\",\"b\"]}",
				getRequest("testid", "json").andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementXmlExtensionIsAccepted() throws Exception {
		assertEquals("<DataElement id=\"testid\" name=\"testname\"><Group>a</Group><Group>b</Group></DataElement>",
				getRequest("testid", "xml").andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementAcceptHeaderJsonIsAccepted() throws Exception {
		assertEquals("{\"id\":\"testid\",\"name\":\"testname\",\"groups\":[\"a\",\"b\"]}",
				getRequest("testid", MediaType.APPLICATION_JSON).andReturn().getResponse().getContentAsString());
	}

	@Test
	@WithMockUser(value = "test", password = "test")
	public void dataElementAcceptHeaderXmlIsAccepted() throws Exception {
		assertEquals("<DataElement id=\"testid\" name=\"testname\"><Group>a</Group><Group>b</Group></DataElement>",
				getRequest("testid", MediaType.APPLICATION_XML).andReturn().getResponse().getContentAsString());
	}


	private void assertResponseStatus(String id, ResultMatcher status) throws Exception {
		getRequest(id).andExpect(status);
	}

	private ResultActions getRequest(String id) throws Exception {
		return getRequest(id, null, null);
	}

	private ResultActions getRequest(String id, MediaType accept) throws Exception {
		return getRequest(id, null, accept);
	}

	private ResultActions getRequest(String id, String extension) throws Exception {
		return getRequest(id, extension, null);
	}

	private ResultActions getRequest(String id, String extension, MediaType accept) throws Exception {
		MockHttpServletRequestBuilder builder = get("/api/dataElements"
				+ (extension == null ? "" : "." + extension)
				+ (id == null ? "" : "?id=" + id));
		if (accept != null)
			builder.accept(accept);
		return mvc.perform(builder);
	}
}
