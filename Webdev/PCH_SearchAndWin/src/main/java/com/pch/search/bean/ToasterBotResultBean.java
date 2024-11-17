package com.pch.search.bean;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ToasterBotResultBean {
	List<String> toastMessages;
	
	public ToasterBotResultBean(List<String> toastMessages){
		this.toastMessages=toastMessages;
	}
	
	public String toString(){
		return StringUtils.join(toastMessages, "|");
	}
	
	public String getToastMessageAtIndex(int index){
		return toastMessages.get(index);
	}
}
