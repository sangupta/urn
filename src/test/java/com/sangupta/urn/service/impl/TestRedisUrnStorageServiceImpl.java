package com.sangupta.urn.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fiftyonred.mock_jedis.MockJedis;
import com.sangupta.dryrun.redis.DryRunRedisTemplate;
import com.sangupta.jerry.util.ByteArrayUtils;
import com.sangupta.urn.service.UrnStorageService;

public class TestRedisUrnStorageServiceImpl {

	@Test
	public void testService() {
		MockJedis jedis = new MockJedis("mock-jedis");
		RedisTemplate<String, byte[]> template = new DryRunRedisTemplate<String, byte[]>(jedis);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new RedisSerializer<byte[]>() {

			@Override
			public byte[] serialize(byte[] t) throws SerializationException {
				return t;
			}

			@Override
			public byte[] deserialize(byte[] bytes) throws SerializationException {
				return bytes;
			}
		});
		
		UrnStorageService service = new RedisUrnStorageServiceImpl(template);
		
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
