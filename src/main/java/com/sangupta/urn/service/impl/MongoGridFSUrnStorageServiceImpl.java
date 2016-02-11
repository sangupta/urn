package com.sangupta.urn.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.urn.model.UrnObject;
import com.sangupta.urn.service.UrnStorageService;

/**
 * A MongoDB GridFS based implementation for {@link UrnStorageService}.
 * 
 * @author sangupta
 *
 */
public class MongoGridFSUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoGridFSUrnStorageServiceImpl.class);
	
	private static final String FIELD_EXPIRY = "expiry"; 
	
	private static final String FIELD_MIME = "mime";

	private static final String FIELD_STORED = "stored";

	private static final String FIELD_NAME = "name";

	private final GridFsTemplate template;
	
	public MongoGridFSUrnStorageServiceImpl(GridFsTemplate template) {
		this.template = template;
	}

	@Override
	protected UrnObject get(String objectKey) {
		Query query = new Query(Criteria.where("filename").is(objectKey));
		GridFSDBFile file = this.template.findOne(query);
		if(file == null) {
			return null;
		}
		
		// check for expiry
		Long expiry = (Long) file.get(FIELD_EXPIRY);
		if(expiry != null) {
			if(expiry.longValue() < System.currentTimeMillis()) {
				this.remove(objectKey);
				return null;
			}
		}
		
		byte[] bytes = null;
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			file.writeTo(stream);
			stream.close();
			bytes = stream.toByteArray();
		} catch(IOException e) {
			LOGGER.error("Error reading file from mongo database for key: " + objectKey, e);
			return null;
		}
		
		UrnObject urnObject = new UrnObject(objectKey, bytes);
		
		// now the metadata
		if(expiry != null) {
			urnObject.expiry = expiry.longValue();
		}
		
		urnObject.name = (String) file.get(FIELD_NAME);
		urnObject.mime = file.getContentType();
		urnObject.stored = file.getUploadDate().getTime();
		
		// return the object
		return urnObject;
	}

	@Override
	protected String save(UrnObject urnObject) {
		ByteArrayInputStream stream = new ByteArrayInputStream(urnObject.bytes);
		
		DBObject metadata = new BasicDBObject();
		metadata.put(FIELD_STORED, urnObject.stored);
		
		if(AssertUtils.isNotEmpty(urnObject.name)) {
			metadata.put(FIELD_NAME, urnObject.name);
		}
		if(AssertUtils.isNotEmpty(urnObject.name)) {
			metadata.put(FIELD_MIME, urnObject.mime);
		}
		if(urnObject.expiry > 0) {
			metadata.put(FIELD_EXPIRY, urnObject.expiry);
		}
		
		GridFSFile file = this.template.store(stream, urnObject.key, urnObject.mime, metadata);
		if(file == null) {
			return null;
		}
		
		return file.getFilename();
	}

	@Override
	protected boolean remove(String objectKey) {
		Query query = new Query(Criteria.where("filename").is(objectKey));
		this.template.delete(query);
		return true;
	}

	@Override
	protected boolean has(String objectKey) {
		Query query = new Query(Criteria.where("filename").is(objectKey));
		GridFSDBFile file = this.template.findOne(query);
		if(file == null) {
			return false;
		}
		
		// check for expiry
		Long expiry = (Long) file.get(FIELD_EXPIRY);
		if(expiry != null) {
			if(expiry.longValue() < System.currentTimeMillis()) {
				this.remove(objectKey);
				return false;
			}
		}
		
		return true;
	}

}
