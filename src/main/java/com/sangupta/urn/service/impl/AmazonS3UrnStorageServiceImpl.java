package com.sangupta.urn.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.urn.model.UrnObject;
import com.sangupta.urn.service.UrnStorageService;

/**
 * An Amazon-S3 based implementation for {@link UrnStorageService} that helps store
 * objects onto Amazon S3.
 * 
 * @author sangupta
 *
 */
public class AmazonS3UrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AmazonS3UrnStorageServiceImpl.class);
	
	/**
	 * The client to be used for working with S3
	 */
	private final AmazonS3Client client;
	
	/**
	 * The bucket under which all objects will be stored
	 */
	private final String bucketName;
	
	/**
	 * Constructor
	 * 
	 * @param client
	 */
	public AmazonS3UrnStorageServiceImpl(AmazonS3Client client, String bucketName) {
		if(client == null) {
			throw new IllegalArgumentException("AmazonS3Client cannot be null");
		}
		
		if(AssertUtils.isEmpty(bucketName)) {
			throw new IllegalArgumentException("Bucket name cannot be null/empty");
		}
		
		this.client = client;
		this.bucketName = bucketName;
	}

	@Override
	protected UrnObject get(String objectKey) {
		S3Object object = this.client.getObject(this.bucketName, objectKey);
		if(object == null) {
			return null;
		}
		
		try {
			InputStream stream = object.getObjectContent();
			
			byte[] bytes = IOUtils.toByteArray(stream);
			
			UrnObject urnObject = new UrnObject(objectKey, bytes);
			
			// TODO: read and populate metadata
			ObjectMetadata metadata = object.getObjectMetadata();
			if(metadata != null) {
				if(metadata.getHttpExpiresDate() != null) {
					urnObject.expiry = metadata.getHttpExpiresDate().getTime();
				}
				
				urnObject.mime = metadata.getContentType();
				urnObject.stored = metadata.getLastModified().getTime();
				
				// TODO:parse the value to extract the filename if available
				urnObject.name = metadata.getContentDisposition();
			}
			
			// return the object
			return urnObject;
		} catch (IOException e) {
			// happens when we cannot read data from S3
			LOGGER.debug("Exception reading data from S3 for object key: " + objectKey, e);
			return null;
		} finally {
			if(object != null) {
				try {
					object.close();
				} catch (IOException e) {
					LOGGER.warn("Unable to close S3 object during/after reading the object");
				}
			}
		}
	}

	@Override
	protected String save(UrnObject urnObject) {
		InputStream stream = new ByteArrayInputStream(urnObject.bytes);
		
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(urnObject.bytes.length);
		
		if(AssertUtils.isNotEmpty(urnObject.name)) {
			metadata.setContentDisposition("filename=" + urnObject.name);
		}
		
		if(AssertUtils.isNotEmpty(urnObject.mime)) {
			metadata.setContentType(urnObject.mime);
		}
		
		if(urnObject.expiry > 0) {
			metadata.setHttpExpiresDate(new Date(urnObject.expiry));
		}
		
		PutObjectResult result = this.client.putObject(this.bucketName, urnObject.key, stream, metadata);
		if(result == null) {
			return null;
		}
		
		return this.client.getResourceUrl(this.bucketName, urnObject.key);
	}

	@Override
	protected boolean remove(String objectKey) {
		this.client.deleteObject(this.bucketName, objectKey);
		return true;
	}

	@Override
	protected boolean has(String objectKey) {
		return this.client.doesObjectExist(this.bucketName, objectKey);
	}
	
}
