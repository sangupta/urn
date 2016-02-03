package com.sangupta.urn.service.impl;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.urn.service.UrnStorageService;

public abstract class AbstractUrnStorageServiceImpl implements UrnStorageService {

	@Override
	public String saveObject(String objectName, byte[] bytes) {
		if (AssertUtils.isEmpty(objectName)) {
			throw new IllegalArgumentException("Object name cannot be null/empty");
		}

		if (bytes == null || bytes.length == 0) {
			throw new IllegalArgumentException("Data bytes to store cannot be null/empty");
		}

		return this.save(objectName, bytes);
	}

	@Override
	public byte[] getObject(String objectName) {
		if (AssertUtils.isEmpty(objectName)) {
			throw new IllegalArgumentException("Object name cannot be null/empty");
		}

		return this.get(objectName);
	}

	@Override
	public boolean removeObject(String objectName) {
		if (AssertUtils.isEmpty(objectName)) {
			throw new IllegalArgumentException("Object name cannot be null/empty");
		}

		return this.remove(objectName);
	}
	
	@Override
	public boolean existsObject(String objectName) {
		// TODO: fix this to improve performance
		byte[] bytes = this.getObject(objectName);
		if(bytes == null) {
			return false;
		}
		
		return true;
	}
	
	// protected methods that implementations need to implement

	protected abstract byte[] get(String objectName);

	protected abstract String save(String objectName, byte[] bytes);

	protected abstract boolean remove(String objectName);

}
