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

package com.sangupta.urn.model;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.jerry.util.ByteArrayUtils;

public class TestUrnObject {

	@Test
	public void testUrnObject() {
		UrnObject one = new UrnObject("key1", ByteArrayUtils.getRandomBytes(1024));
		UrnObject two = new UrnObject("key2", ByteArrayUtils.getRandomBytes(1024));
		UrnObject undefined = new UrnObject();
		
		UrnObject oneClone = new UrnObject("key1", ByteArrayUtils.getRandomBytes(1024));
		UrnObject twoClone = new UrnObject("key2", ByteArrayUtils.getRandomBytes(1024));
		
		Assert.assertEquals(one, one);
		Assert.assertEquals(one, oneClone);
		
		Assert.assertEquals(two, two);
		Assert.assertEquals(two, twoClone);
		
		Assert.assertNotEquals(one, two);
		Assert.assertNotEquals(one, undefined);
		Assert.assertNotEquals(undefined, two);
		
		// hashcode
		Assert.assertEquals(one.hashCode(), oneClone.hashCode());
		Assert.assertEquals(two.hashCode(), twoClone.hashCode());
		
		Assert.assertEquals(0, undefined.hashCode());
		
		Assert.assertNotEquals(one.hashCode(), two.hashCode());
		Assert.assertNotEquals(one.hashCode(), undefined.hashCode());
		Assert.assertNotEquals(undefined.hashCode(), two.hashCode());
	}
	
}