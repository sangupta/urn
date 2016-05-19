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

/**
 * Holds only the metadata for an object stored in the urn.
 * 
 * @author sangupta
 *
 */
public class UrnObjectMeta {

	/**
	 * The human readable name for this object
	 */
	public String name;
	
	/**
	 * The associated MIME header
	 */
	public String mime;
	
	/**
	 * Time at which the object was stored
	 */
	public long stored;
	
	/**
	 * Time at which the object will expire
	 */
	public long expiry = -1;

	/**
	 * Default constructor
	 */
	public UrnObjectMeta() {
		this.stored = System.currentTimeMillis();
	}
	
}