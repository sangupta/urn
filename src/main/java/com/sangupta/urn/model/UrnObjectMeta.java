package com.sangupta.urn.model;

/**
 * Holds only the metadata for an object stored in the urn.
 * 
 * @author sangupta
 *
 */
public class UrnObjectMeta {

	/**
	 * The human readable name for this object
	 */
	public String name;
	
	/**
	 * The associated MIME header
	 */
	public String mime;
	
	/**
	 * Time at which the object was stored
	 */
	public long stored;
	
	/**
	 * Time at which the object will expire
	 */
	public long expiry = -1;

	/**
	 * Default constructor
	 */
	public UrnObjectMeta() {
		this.stored = System.currentTimeMillis();
	}
	
}
