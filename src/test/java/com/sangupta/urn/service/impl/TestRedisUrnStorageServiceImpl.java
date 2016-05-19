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

import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.sangupta.dryredis.DryRedis;
import com.sangupta.dryrun.redis.DryRunRedisTemplate;
import com.sangupta.urn.service.UrnStorageService;

public class TestRedisUrnStorageServiceImpl extends AbstractUrnStorageTests {

	@Test
	public void testService() {
//	    DryRedis dryRedis = DryRedis.getDatabase("test");
//	    DryRunRedisTemplate<String, byte[]> template = new DryRunRedisTemplate<String, byte[]>(dryRedis);
//		
//		StringRedisSerializer defaultSerializer = new StringRedisSerializer();
//		template.setKeySerializer(defaultSerializer);
//		template.setValueSerializer(new RedisSerializer<byte[]>() {
//
//			@Override
//			public byte[] serialize(byte[] t) throws SerializationException {
//				return t;
//			}
//
//			@Override
//			public byte[] deserialize(byte[] bytes) throws SerializationException {
//				return bytes;
//			}
//		});
//		
//		RedisTemplate<String, String> metaTemplate = new DryRunRedisTemplate<String, String>(dryRedis);
//		metaTemplate.setKeySerializer(defaultSerializer);
//		metaTemplate.setValueSerializer(defaultSerializer);
//		
//		UrnStorageService service = new RedisUrnStorageServiceImpl(template, metaTemplate);
//		
//		testService(service);
	}

}