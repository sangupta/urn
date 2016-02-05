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