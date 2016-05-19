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

import com.sangupta.urn.model.UrnObject;

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
	 * @param objectKey
	 *            the name of the object or its unique key
	 * 
	 * @param bytes
	 *            the byte-array contents for the object
	 * 
	 * @return the implementation specific unique name/url for this object
	 * 
	 */
	public String saveObject(String objectKey, byte[] bytes);
	
	/**
	 * Save the given object in the urn with its detailed metadata. If an object
	 * with the same name already exists, it will be over-written.
	 * 
	 * @param objectKey
	 *            the unique key for the object
	 * 
	 * @param name
	 *            the human-readable name for the object
	 * 
	 * @param bytes
	 *            the byte-array contents for the object
	 * 
	 * @param mimeType
	 *            the MIME associated with this object
	 * 
	 * @param expiry
	 *            the time after which the object should be considered expired
	 *            specified as epoch millis.
	 * 
	 * @return the implementation specific unique name/url for this object
	 */
	public String saveObject(String objectKey, String name, byte[] bytes, String mimeType, long expiry);
	
	/**
	 * Read the object with the given name from the urn.
	 * 
	 * @param objectKey
	 *            the name of the object or its unique key
	 * 
	 * @return the binary contents of the object, or <code>null</code> if the
	 *         object is not found or cannot be read
	 */
	public byte[] getObjectBytes(String objectKey);
	
	/**
	 * Return the detailed {@link UrnObject} that is associated with the given
	 * name from the urn.
	 * 
	 * @param objectKey
	 *            the name of hte object or its unique key
	 * 
	 * @return the {@link UrnObject} instance if object is found, or
	 *         <code>null</code> if no such object exists
	 */
	public UrnObject getObject(String objectKey);
	
	/**
	 * Remove the object with the given name from the urn.
	 * 
	 * @param objectKey
	 *            the name of the object or its unique key
	 * 
	 * @return <code>true</code> if the object was removed or did not exists,
	 *         <code>false</code> if removal failed
	 */
	public boolean removeObject(String objectKey);
	
	/**
	 * Check if an object with the given name already exists in the urn.
	 * 
	 * @param objectKey
	 *            the name of the object or its unique key
	 *            
	 * @return <code>true</code> if object exists, <code>false</code> otherwise
	 */
	public boolean existsObject(String objectKey);
	
}