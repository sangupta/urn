package com.sangupta.urn.model;

import org.springframework.data.annotation.Id;

public class UrnObject {

	@Id
	public String name;
	
	public byte[] bytes;
	
	public long millis;
	
	public UrnObject() {
		
	}

	public UrnObject(String name, byte[] bytes) {
		this.name = name;
		this.bytes = bytes;
		this.millis = System.currentTimeMillis();
	}
	
}
