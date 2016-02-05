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
 

package com.sangupta.urn.service;

/**
 * Contract for all storage services that want to provide
 * an implementation for storage of binary objects as part
 * of the URN library.
 * 
 * @author sangupta
 *
 */
public interface UrnStorageService {

	/**
	 * Save the given object in the urn. If an object with the same name
	 * already exists, it will be over-written.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 * 
	 * @param bytes
	 *            the byte-array contents for the object
	 * 
	 * @return the implementation specific unique name/url for this object
	 * 
	 */
	public String saveObject(String objectName, byte[] bytes);
	
	/**
	 * Read the object with the given name from the urn.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 * 
	 * @return the binary contents of the object, or <code>null</code> if the
	 *         object is not found or cannot be read
	 */
	public byte[] getObject(String objectName);
	
	/**
	 * Remove the object with the given name from the urn.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 * 
	 * @return <code>true</code> if the object was removed or did not exists,
	 *         <code>false</code> if removal failed
	 */
	public boolean removeObject(String objectName);
	
	/**
	 * Check if an object with the given name already exists in the urn.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 *            
	 * @return <code>true</code> if object exists, <code>false</code> otherwise
	 */
	public boolean existsObject(String objectName);
	
}