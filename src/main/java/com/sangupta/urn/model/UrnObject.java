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

/**
 * An object stored in the urn along with its metadata.
 * 
 * @author sangupta
 *
 */
public class UrnObject extends UrnObjectMeta {

	/**
	 * The unique key for this object inside the urn
	 */
	@Id
	public String key;
	
	/**
	 * The byte-array contents for this object
	 */
	public byte[] bytes;
	
	/**
	 * Default constructor
	 */
	public UrnObject() {
		super();
	}

	/**
	 * Convenience constructor
	 * 
	 * @param key
	 * @param bytes
	 */
	public UrnObject(String key, byte[] bytes) {
		this();
		this.key = key;
		this.bytes = bytes;
	}
	
	@Override
	public int hashCode() {
		if(this.key == null) {
			return 0;
		}
		
		return this.key.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		
		if(obj == this) {
			return true;
		}
		
		if(this.key == null) {
			return false;
		}
		
		if(!(obj instanceof UrnObject)) {
			return false;
		}
		
		UrnObject other = (UrnObject) obj;
		return this.key.equals(other.key);
	}
	
	@Override
	public String toString() {
		return "[UrnObject: key=" + this.key + ", name=" + this.name + "]";
	}

	/**
	 * Return a new {@link UrnObjectMeta} instance that has the same properties
	 * as this instance, but both are de-linked.
	 *  
	 * @return
	 */
	public UrnObjectMeta cloneObjectMeta() {
		UrnObjectMeta meta = new UrnObjectMeta();
		
		meta.name = this.name;
		meta.expiry = this.expiry;
		meta.mime = this.mime;
		meta.stored = this.stored;
		
		return meta;
	}

	public boolean isExpired() {
		if(this.expiry <= 0) {
			return false;
		}
		
		if(System.currentTimeMillis() > this.expiry) {
			return true;
		}
		
		return false;
	}
}