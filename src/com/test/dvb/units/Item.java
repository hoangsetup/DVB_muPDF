package com.test.dvb.units;

public class Item {
	private String name;
	private boolean isFile;
	
	public Item(){
		
	}
	
	public Item(String name, boolean isFile){
		this.name = name;
		this.isFile = isFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFile() {
		return isFile;
	}

	public void setFile(boolean isFile) {
		this.isFile = isFile;
	}
	
	
}
