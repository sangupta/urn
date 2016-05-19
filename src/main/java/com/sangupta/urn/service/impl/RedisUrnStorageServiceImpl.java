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

import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.urn.model.UrnObject;
import com.sangupta.urn.model.UrnObjectMeta;

public class RedisUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private final RedisTemplate<String, byte[]> redisTemplate;
	
	private final RedisTemplate<String, String> metaTemplate;
	
	public RedisUrnStorageServiceImpl(RedisTemplate<String, byte[]> redisTemplate, RedisTemplate<String, String> metaTemplate) {
		this.redisTemplate = redisTemplate;
		this.metaTemplate = metaTemplate;
	}
	
	@Override
	protected UrnObject get(String objectKey) {
		String json = this.metaTemplate.opsForValue().get(getMetaKey(objectKey));
		if(json == null) {
			return null;
		}
		
		UrnObject urnObject = GsonUtils.getGson().fromJson(json, UrnObject.class);
		
		byte[] bytes = this.redisTemplate.opsForValue().get(objectKey);
		urnObject.key = objectKey;
		urnObject.bytes = bytes;
		
		return urnObject;
	}

	@Override
	protected String save(UrnObject urnObject) {
		this.redisTemplate.opsForValue().set(urnObject.key, urnObject.bytes);
		
		UrnObjectMeta meta = urnObject.cloneObjectMeta();
		this.metaTemplate.opsForValue().set(getMetaKey(urnObject.key), GsonUtils.getGson().toJson(meta));
		
		return urnObject.key;
	}
	
	@Override
	protected boolean remove(String objectKey) {
		// first remove the meta so that no call to read can succeed
		this.metaTemplate.delete(getMetaKey(objectKey));
		this.redisTemplate.delete(objectKey);
		return true;
	}

	@Override
	protected boolean has(String objectKey) {
		// TODO: fix via redis.hasKey()
		UrnObject urnObject = this.get(objectKey);
		if(urnObject == null) {
			return false;
		}
		
		if(urnObject.isExpired()) {
			this.remove(objectKey);
			return false;
		}
		
		return true;
	}

}