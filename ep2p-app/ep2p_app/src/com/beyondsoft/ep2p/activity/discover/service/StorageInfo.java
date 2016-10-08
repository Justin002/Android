package com.beyondsoft.ep2p.activity.discover.service;
public class StorageInfo {
	public String path;
	public String state;
	public boolean isRemoveable;
 
	public StorageInfo(String path) {
		this.path = path;
	}
 
	public boolean isMounted() {
		return "mounted".equals(state);
	}
}