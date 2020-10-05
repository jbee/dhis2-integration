package com.example.dhis2.backend;

/**
 * Gives access to the raw data as exposed by the backend
 */
public interface BackendService {

	/**
	 * @return All {@link DataElements} known to the backend
	 */
	DataElements loadDataElements();

	/**
	 * @return All {@link DataElementGroups} known to the backend
	 */
	DataElementGroups loadDataElementGroups();
}
