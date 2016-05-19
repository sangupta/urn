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

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.sangupta.jerry.util.GsonUtils;
import com.sangupta.jerry.util.StringUtils;
import com.sangupta.urn.model.UrnObject;
import com.sangupta.urn.model.UrnObjectMeta;
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
	protected String save(UrnObject urnObject) {
		File file = getFile(urnObject.key);
		String result = null;
		
		try {
			FileUtils.writeByteArrayToFile(file, urnObject.bytes);
			result = file.getAbsolutePath();
		} catch (IOException e) {
			FileUtils.deleteQuietly(file);
			return null;
		}
		
		// and the meta in a different file
		// store the metadata in a different file
		UrnObjectMeta meta = urnObject.cloneObjectMeta();
		if(meta != null) {
			file = getFile(getMetaKey(urnObject.key));
			
			try {
				String json = GsonUtils.getGson().toJson(meta);
				FileUtils.writeByteArrayToFile(file, json.getBytes(StringUtils.CHARSET_UTF8));
			} catch (IOException e) {
				// eat up
			}
		}
		
		return result;
	}

	@Override
	protected UrnObject get(String objectKey) {
		try {
			// read actual data
			File file = getFile(objectKey);
			byte[] data = FileUtils.readFileToByteArray(file);
			
			// read metadata
			UrnObject urnObject = getMetaObject(objectKey);
			
			urnObject.key = objectKey;
			urnObject.bytes = data;
			
			return urnObject;
		} catch (IOException e) {
			return null;
		}
	}
	
	@Override
	protected boolean remove(String objectKey) {
		File file = getFile(objectKey);
		try {
			FileUtils.forceDelete(file);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		file = getFile(getMetaKey(objectKey));
		try {
			FileUtils.forceDelete(file);
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	protected boolean has(String objectKey) {
		File data = getFile(objectKey);

		UrnObject urnObject = null;
		try {
			urnObject = getMetaObject(objectKey);
		} catch (IOException e) {
			return false;
		}
		
		if(urnObject == null) {
			return false;
		}
		
		if(urnObject.isExpired()) {
			this.remove(objectKey);
			return false;
		}
		
		return asFileExists(data);
	}
	
	/**
	 * Convert the given object name into a corresponding {@link File} object
	 * within the {@link #rootFolder} that is provided.
	 * 
	 * @param objectName the name of the object being stored
	 * 
	 * @return
	 */
	protected File getFile(String objectKey) {
		String base = getFolder(objectKey);
		File folder = new File(this.rootFolder, base);
		folder.mkdirs();
		
		return new File(folder, objectKey);
	}

	protected String getFolder(String objectKey) {
		int len = objectKey.length();
		
		final String firstLevelFolder = objectKey.substring(0, 2);
		final String secondLevelFolder = objectKey.substring(len - 2);
		
		return firstLevelFolder + File.separator + secondLevelFolder;
	}

	/**
	 * Check if the given {@link File} instance represents an existing file on
	 * disk.
	 * 
	 * @param file
	 *            the {@link File} instance to be tested
	 * 
	 * @return <code>true</code> if file represents a valid existing file on
	 *         disk, <code>false</code> otherwise
	 */
	private boolean asFileExists(File file) {
		if(file == null) {
			return false;
		}
		
		if(!file.exists()) {
			return false;
		}
		
		if(!file.isFile()) {
			return false;
		}
		
		return true;
	}
	
	private UrnObject getMetaObject(String objectKey) throws IOException {
		File file = getFile(getMetaKey(objectKey));
		byte[] bytes = FileUtils.readFileToByteArray(file);
		
		String json = new String(bytes, StringUtils.CHARSET_UTF8);
		
		// copy metadata
		return GsonUtils.getGson().fromJson(json, UrnObject.class);
	}

}