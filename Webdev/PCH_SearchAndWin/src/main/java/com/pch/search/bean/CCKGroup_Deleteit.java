package com.pch.search.bean;

import java.util.ArrayList;
import java.util.List;

public class CCKGroup_Deleteit {

	private List<JoomlaProperty> properties;

	public List<JoomlaProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<JoomlaProperty> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "CCKGroup [properties=" + properties + "]";
	}
	
	public void addProperty(JoomlaProperty property){
		if(properties==null){
			properties=new ArrayList<JoomlaProperty>();
		}
		
		properties.add(property);
	}
	
	
	
}
