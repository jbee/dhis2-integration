package com.example.dhis2.integration;

/**
 * Gives access to the integrated data (cached and mapped).
 */
public interface IntegrationService {

	/**
	 * @param id an element ID, not null
	 * @return The {@link DataElement} for the given ID or null if no such element exists
	 */
	DataElement getDataElementById(String id);

	/**
	 * @param id a group ID, not null
	 * @return The {@link DataElementGroup} for the given ID or null if no such group exists
	 */
	DataElementGroup getDataElementGroupById(String id);
}
