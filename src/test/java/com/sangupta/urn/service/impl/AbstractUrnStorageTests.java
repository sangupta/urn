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

import org.junit.Assert;

import com.sangupta.jerry.constants.HttpMimeType;
import com.sangupta.jerry.util.ByteArrayUtils;
import com.sangupta.urn.service.UrnStorageService;

/**
 * Common tests to run over a given {@link UrnStorageService} implementation.
 * 
 * @author sangupta
 *
 */
public class AbstractUrnStorageTests {

	protected void testService(UrnStorageService service) {
		byte[] data1 = ByteArrayUtils.getRandomBytes(1024);
		byte[] data2 = ByteArrayUtils.getRandomBytes(1024);
		
		String keyName = "key1";
		
		Assert.assertFalse(service.existsObject(keyName));
		Assert.assertNotNull(service.saveObject(keyName, data1));
		Assert.assertTrue(service.existsObject(keyName));
		Assert.assertArrayEquals(data1, service.getObjectBytes(keyName));
		Assert.assertNotNull(service.saveObject(keyName, data2));
		Assert.assertTrue(service.existsObject(keyName));
		Assert.assertArrayEquals(data2, service.getObjectBytes(keyName));
		Assert.assertTrue(service.removeObject(keyName));
		Assert.assertFalse(service.existsObject(keyName));
		
		// test for expired object
		String mimeType = HttpMimeType.BINARY;
		
		// add an expired object - should not be added
		Assert.assertNull(service.saveObject(keyName, keyName, data1, mimeType, 10));
		Assert.assertFalse(service.existsObject(keyName));
		
		// add fresh object
		Assert.assertNotNull(service.saveObject(keyName, keyName, data1, mimeType, System.currentTimeMillis() + 100));
		Assert.assertTrue(service.existsObject(keyName));
		// sleep for a while
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// eat up
		}
		
		// must not be present
		Assert.assertFalse(service.existsObject(keyName));
	}
}