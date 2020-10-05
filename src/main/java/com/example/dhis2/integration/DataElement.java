package com.example.dhis2.integration;

import com.example.dhis2.backend.DataElements;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public final class DataElement {

	@JacksonXmlProperty(isAttribute = true)
	public final String id;
	@JacksonXmlProperty(isAttribute = true)
	public final String name;
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Group")
	public final String[] groups;

	public DataElement(DataElements.DataElement element) {
		this.id = element.getId();
		this.name = element.getDisplayName();
		this.groups = element.getDataElementGroups().stream()
				.map(DataElements.DataElementGroup::getName)
				.toArray(String[]::new);
	}

	public String getName() {
		return name;
	}

	public String[] getGroups() {
		return groups;
	}

	public String getId() {
		return id;
	}
}
