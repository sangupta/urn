package com.sangupta.urn.service.impl;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.jerry.util.ByteArrayUtils;
import com.sangupta.urn.service.UrnStorageService;

public class TestInMemoryUrnStorageServiceImpl {

	@Test
	public void testService() {
		UrnStorageService service = new InMemoryUrnStorageServiceImpl();
		
		byte[] data1 = ByteArrayUtils.getRandomBytes(1024);
		byte[] data2 = ByteArrayUtils.getRandomBytes(1024);
		
		String keyName = "key1";
		
		Assert.assertFalse(service.existsObject(keyName));
		Assert.assertNotNull(service.saveObject(keyName, data1));
		Assert.assertTrue(service.existsObject(keyName));
		Assert.assertArrayEquals(data1, service.getObject(keyName));
		Assert.assertNotNull(service.saveObject(keyName, data2));
		Assert.assertTrue(service.existsObject(keyName));
		Assert.assertArrayEquals(data2, service.getObject(keyName));
		Assert.assertTrue(service.removeObject(keyName));
		Assert.assertFalse(service.existsObject(keyName));
	}
	
}
