package com.pch.search.bean;


public class JoomlaProperty {
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public enum PropertyType{TEXT,CHECKBOX,SELECT};
	String key;
	String value;
	PropertyType type;
	int groupIndex =0;
	
	public int getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(int groupIndex) {
		this.groupIndex = groupIndex;
	}

	public JoomlaProperty(PropertyType type,String key, String value,int groupIndex) {
		super();
		this.key = key;
		this.value = value;
		this.type = type;
		this.groupIndex = groupIndex;
	}

	@Override
	public String toString() {
		return "JoomlaProperty [key=" + key + ", value=" + value + ", type="
				+ type + ", groupIndex=" + groupIndex + "]";
	}
	
	
	
}
