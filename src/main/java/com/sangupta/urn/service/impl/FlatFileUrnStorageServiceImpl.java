package com.sangupta.urn.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sangupta.urn.service.UrnStorageService;

/**
 * A simple flat-file based {@link UrnStorageService} that stores files inside
 * child folders and tries to minimize the number of files/folders inside a
 * child folder - by creating a two-level nesting of child folders that is
 * derived from the object name itself. For a million objects thus stored only
 * 15 files are created in any given folder.
 * 
 * @author sangupta
 *
 */
public class FlatFileUrnStorageServiceImpl extends AbstractUrnStorageServiceImpl {
	
	/**
	 * The root folder where the file is to be stored
	 */
	private final File rootFolder;
	
	/**
	 * Convenience constructor
	 * 
	 * @param rootFolder the root folder where files are stored
	 * 
	 */
	public FlatFileUrnStorageServiceImpl(File rootFolder) {
		this.rootFolder = rootFolder;
	}

	@Override
	protected String save(String objectName, byte[] bytes) {
		File file = getFile(objectName);
		
		try {
			FileUtils.writeByteArrayToFile(file, bytes);
			return file.getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected byte[] get(String objectName) {
		File file = getFile(objectName);
		if(!file.exists()) {
			return null;
		}
		
		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected boolean remove(String objectName) {
		File file = getFile(objectName);
		if(!file.exists()) {
			return true;
		}
		
		try {
			FileUtils.forceDelete(file);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Convert the given object name into a corresponding {@link File} object
	 * within the {@link #rootFolder} that is provided.
	 * 
	 * @param objectName the name of the object being stored
	 * 
	 * @return
	 */
	protected File getFile(String objectName) {
		String base = getFolder(objectName);
		File folder = new File(this.rootFolder, base);
		folder.mkdirs();
		
		return new File(folder, objectName);
	}

	protected String getFolder(String objectName) {
		int len = objectName.length();
		
		final String firstLevelFolder = objectName.substring(0, 2);
		final String secondLevelFolder = objectName.substring(len - 2);
		
		return firstLevelFolder + File.separator + secondLevelFolder;
	}
	
}
