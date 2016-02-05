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
import org.junit.Test;

import com.sangupta.jerry.constants.SystemPropertyNames;
import com.sangupta.urn.service.UrnStorageService;

public class TestFlatFileUrnStorageServiceImpl extends AbstractUrnStorageTests {

	@Test
	public void testService() {
		File rootFolder = new File(System.getProperty(SystemPropertyNames.JAVA_TMPDIR), "test");
		rootFolder.mkdirs();
		
		UrnStorageService service = new FlatFileUrnStorageServiceImpl(rootFolder);
		
		testService(service);	
		
		// clean up
		try {
			FileUtils.deleteDirectory(rootFolder);
		} catch (IOException e) {
			// eat up
		}
	}
	
}