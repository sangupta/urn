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

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.sangupta.urn.model.UrnObject;
import com.sangupta.urn.service.UrnStorageService;

/**
 * An implementation of {@link UrnStorageService} that uses MongoDB for storage
 * and stores each object in a collection that is specified when creating an
 * instance of this implementation.
 * 
 * @author sangupta
 *
 */
public class MongoCollectionUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private final MongoTemplate mongoTemplate;
	
	private final String collectionName;
	
	public MongoCollectionUrnStorageServiceImpl(MongoTemplate mongoTemplate, String collectionName) {
		this.mongoTemplate = mongoTemplate;
		this.collectionName = collectionName;
	}
	
	@Override
	protected String save(UrnObject urnObject) {
		this.mongoTemplate.save(urnObject, this.collectionName);
		return urnObject.key;
	}
	
	@Override
	protected UrnObject get(String objectKey) {
		return this.mongoTemplate.findById(objectKey, UrnObject.class, this.collectionName);
	}

	@Override
	protected boolean remove(String objectKey) {
		Query query = new Query(Criteria.where("key").is(objectKey));
		this.mongoTemplate.findAndRemove(query, UrnObject.class, this.collectionName);
		return true;
	}

}