package com.pch.search.utilities;


public class TextBoxElement {
	HtmlElement element;
	protected TextBoxElement(HtmlElement element){
		this.element=element;
	}
	
	public String getText(){
		return element.getAttribute("value");
	}
	
	public void setText(String text){
		element.clear();
		element.sendKeys(text);
	}
	
	public boolean isReadOnly(){
		if((element.getAttribute("readonly")!=null)||
		   (element.getAttribute("disabled")!=null)){
			return true;
		}else{
			return false;
		}
	}
}
