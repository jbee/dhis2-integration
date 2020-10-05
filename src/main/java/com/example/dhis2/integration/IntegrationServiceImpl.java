package com.example.dhis2.integration;

import com.example.dhis2.backend.BackendService;
import com.example.dhis2.backend.DataElementGroups;
import com.example.dhis2.backend.DataElements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class IntegrationServiceImpl implements IntegrationService {

	private static final int ONE_MINUTE = 60_000;

	private Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);

	private final ConcurrentMap<String, DataElement> elementsById = new ConcurrentHashMap<>();
	private final ConcurrentMap<String, DataElementGroup> groupsById = new ConcurrentHashMap<>();

	private final BackendService backend;

	@Autowired
	public IntegrationServiceImpl(BackendService backend) {
		this.backend = backend;
		reloadCache(); // first load is synchronous to make sure cache is filled when API is ready (and also tests aren't flaky)
	}

	@Scheduled(fixedRate = ONE_MINUTE, initialDelay = ONE_MINUTE)
	public void reloadCache() {
		populateDataElements();
		populateDataElementGroups();
	}

	private void populateDataElements() {
		DataElements dataElements = backend.loadDataElements();
		for (DataElements.DataElement e : dataElements.getDataElements()) {
			elementsById.put(e.getId(), new DataElement(e));
		}
		logger.info("Cached " + dataElements.getDataElements().size() + " elements");
	}

	private void populateDataElementGroups() {
		DataElementGroups dataElementGroups = backend.loadDataElementGroups();
		for (DataElementGroups.DataElementGroup e : dataElementGroups.getDataElementGroups()) {
			groupsById.put(e.getId(), new DataElementGroup(e));
		}
		logger.info("Cached " + dataElementGroups.getDataElementGroups().size() + " groups");
	}

	@Override
	public DataElement getDataElementById( String id) {
		return elementsById.get(id);
	}

	@Override
	public DataElementGroup getDataElementGroupById(String id) {
		return groupsById.get(id);
	}
}
