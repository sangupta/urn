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
import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.fakemongo.Fongo;
import com.sangupta.urn.service.UrnStorageService;

public class TestMongoCollectionUrnStorageServiceImpl extends AbstractUrnStorageTests {

	@Test
	public void testService() {
		Fongo fongo = new Fongo("Fake-Mongo-Server");
		MongoTemplate template = new MongoTemplate(fongo.getMongo(), "urn"); 
		UrnStorageService service = new MongoCollectionUrnStorageServiceImpl(template, "random");
		
		testService(service);
	}
	
}