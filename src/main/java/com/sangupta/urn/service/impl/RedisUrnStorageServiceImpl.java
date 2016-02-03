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
