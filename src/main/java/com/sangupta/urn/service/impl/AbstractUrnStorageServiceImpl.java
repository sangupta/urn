/**
 *
 * urn - Object Storage Library
 * Copyright (c) 2016, Sandeep Gupta
 * 
 * http://sangupta.com/projects/urn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
 
package com.sangupta.urn.service.impl;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.urn.model.UrnObject;
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

		return this.save(new UrnObject(objectName, bytes));
	}
	
	@Override
	public String saveObject(String objectKey, String name, byte[] bytes, String mimeType, long expiry) {
		// check for expiry time
		if(expiry > 0 && expiry < System.currentTimeMillis()) {
			return null; // do not save
		}
		
		UrnObject urnObject = new UrnObject(objectKey, bytes);
		
		urnObject.name = name;
		urnObject.mime = mimeType;
		urnObject.expiry = expiry;
		
		return this.save(urnObject);
	}
	
	@Override
	public UrnObject getObject(String objectKey) {
		if (AssertUtils.isEmpty(objectKey)) {
			throw new IllegalArgumentException("Object name cannot be null/empty");
		}

		UrnObject object = this.get(objectKey);

		if(object == null) {
			return null;
		}
		
		// additional expiry check in case implementations are nasty
		if(object.isExpired()) {
			// delete this object
			this.remove(objectKey);
			return null;
		}
		
		return object;
	}

	@Override
	public byte[] getObjectBytes(String objectKey) {
		UrnObject object = this.getObject(objectKey);
		
		if(object == null) {
			return null;
		}
		
		return object.bytes;
	}

	@Override
	public boolean removeObject(String objectName) {
		if (AssertUtils.isEmpty(objectName)) {
			throw new IllegalArgumentException("Object name cannot be null/empty");
		}

		return this.remove(objectName);
	}
	
	@Override
	public boolean existsObject(String objectKey) {
		return this.has(objectKey);
	}
	
	// helper methods for child implementations
	
	/**
	 * Returns a unique meta-data key for a given object key. This is useful
	 * when implementations need to store meta-data as a different object in
	 * their data-store.
	 * 
	 * @param objectKey
	 *            the object key to convert meta key into
	 * 
	 * @return the converted meta key
	 */
	protected String getMetaKey(String objectKey) {
		return objectKey + ".urn.meta";
	}

	// protected methods that implementations need to implement

	protected abstract UrnObject get(String objectKey);

	protected abstract String save(UrnObject urnObject);

	protected abstract boolean remove(String objectKey);
	
	protected abstract boolean has(String objectKey);

}