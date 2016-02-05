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

import org.springframework.data.redis.core.RedisTemplate;

public class RedisUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private final RedisTemplate<String, byte[]> redisTemplate;
	
	public RedisUrnStorageServiceImpl(RedisTemplate<String, byte[]> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected byte[] get(String objectName) {
		return this.redisTemplate.opsForValue().get(objectName);
	}

	@Override
	protected String save(String objectName, byte[] bytes) {
		this.redisTemplate.opsForValue().set(objectName, bytes);
		return objectName;
	}

	@Override
	protected boolean remove(String objectName) {
		this.redisTemplate.delete(objectName);
		return true;
	}


}