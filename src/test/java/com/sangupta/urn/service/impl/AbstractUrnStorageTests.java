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
