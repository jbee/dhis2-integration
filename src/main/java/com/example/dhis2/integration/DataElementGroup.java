package com.example.dhis2.integration;

import com.example.dhis2.backend.DataElementGroups;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public final class DataElementGroup {

	@JacksonXmlProperty(isAttribute = true)
	private final String id;
	@JacksonXmlProperty(isAttribute = true)
	private final String name;
	@JacksonXmlElementWrapper(useWrapping = false)
	@JacksonXmlProperty(localName = "Member")
	private final String[] members;

	public DataElementGroup(DataElementGroups.DataElementGroup group) {
		this.id = group.getId();
		this.name = group.getDisplayName();
		this.members = group.getDataElements().stream()
				.map(DataElementGroups.DataElement::getName)
				.toArray(String[]::new);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String[] getMembers() {
		return members;
	}
}
