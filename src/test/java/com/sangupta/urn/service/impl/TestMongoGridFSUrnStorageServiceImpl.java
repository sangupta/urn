package com.sangupta.urn.service.impl;

import org.junit.Test;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.sangupta.dryrun.mongo.DryRunGridFSTemplate;
import com.sangupta.urn.service.UrnStorageService;

public class TestMongoGridFSUrnStorageServiceImpl extends AbstractUrnStorageTests {

	@Test
	public void testService() {
		GridFsTemplate template = new DryRunGridFSTemplate("test-bucket");
		UrnStorageService service = new MongoGridFSUrnStorageServiceImpl(template);
		testService(service);
	}
	
}
