package com.sangupta.urn.service;

/**
 * Contract for all storage services that want to provide
 * an implementation for storage of binary objects as part
 * of the URN library.
 * 
 * @author sangupta
 *
 */
public interface UrnStorageService {

	/**
	 * Save the given object in the urn. If an object with the same name
	 * already exists, it will be over-written.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 * 
	 * @param bytes
	 *            the byte-array contents for the object
	 * 
	 * @return the implementation specific unique name/url for this object
	 * 
	 */
	public String saveObject(String objectName, byte[] bytes);
	
	/**
	 * Read the object with the given name from the urn.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 * 
	 * @return the binary contents of the object, or <code>null</code> if the
	 *         object is not found or cannot be read
	 */
	public byte[] getObject(String objectName);
	
	/**
	 * Remove the object with the given name from the urn.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 * 
	 * @return <code>true</code> if the object was removed or did not exists,
	 *         <code>false</code> if removal failed
	 */
	public boolean removeObject(String objectName);
	
	/**
	 * Check if an object with the given name already exists in the urn.
	 * 
	 * @param objectName
	 *            the name of the object or its unique key
	 *            
	 * @return <code>true</code> if object exists, <code>false</code> otherwise
	 */
	public boolean existsObject(String objectName);
	
}
