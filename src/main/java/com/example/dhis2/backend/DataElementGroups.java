package com.example.dhis2.backend;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Model as exposed by the https://play.dhis2.org/2.34.1/api/29/dataElementGroups.json backend
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataElementGroups {

	private List<DataElementGroup> dataElementGroups;

	public List<DataElementGroup> getDataElementGroups() {
		return dataElementGroups;
	}

	public void setDataElementGroups(List<DataElementGroup> dataElementGroups) {
		this.dataElementGroups = dataElementGroups;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class DataElementGroup {

		private String id;
		private String displayName;
		private List<DataElement> dataElements;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public List<DataElement> getDataElements() {
			return dataElements;
		}

		public void setDataElements(List<DataElement> dataElements) {
			this.dataElements = dataElements;
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class DataElement {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public DataElement() {
			// make sure exists
		}

		public DataElement(String name) {
			this.name = name;
		}
	}
}
