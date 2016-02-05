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
 

package com.sangupta.urn.model;

import org.springframework.data.annotation.Id;

public class UrnObject {

	@Id
	public String name;
	
	public byte[] bytes;
	
	public long millis;
	
	public UrnObject() {
		
	}

	public UrnObject(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
		this.millis = System.currentTimeMillis();
	}
	
}