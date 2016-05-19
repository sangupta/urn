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

import com.sangupta.urn.model.UrnObject;

public class InMemoryUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private final Map<String, UrnObject> storage = new HashMap<String, UrnObject>();
	
	@Override
	protected UrnObject get(String objectKey) {
		return this.storage.get(objectKey);
	}
	
	@Override
	protected String save(UrnObject urnObject) {
		this.storage.put(urnObject.key, urnObject);
		return urnObject.key;
	}

	@Override
	protected boolean remove(String objectKey) {
		this.storage.remove(objectKey);
		return true;
	}

	@Override
	protected boolean has(String objectKey) {
		if(!this.storage.containsKey(objectKey)) {
			return false;
		}
		
		UrnObject object = this.get(objectKey);
		if(object == null) {
			return false;
		}
		
		if(object.isExpired()) {
			this.remove(objectKey);
			return false;
		}
		
		return true;
	}
	
}