package com.sangupta.urn.service.impl;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private final Map<String, byte[]> storage = new HashMap<String, byte[]>(); 

	@Override
	protected byte[] get(String objectName) {
		return this.storage.get(objectName);
	}

	@Override
	protected String save(String objectName, byte[] bytes) {
		this.storage.put(objectName, bytes);
		return objectName;
	}

	@Override
	protected boolean remove(String objectName) {
		this.storage.remove(objectName);
		return true;
	}

}
