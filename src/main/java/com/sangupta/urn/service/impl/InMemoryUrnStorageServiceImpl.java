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