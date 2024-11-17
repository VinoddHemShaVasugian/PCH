package com.pch.search.utilities;

public class CheckBoxElement {
	HtmlElement element;
	protected CheckBoxElement(HtmlElement element){
		this.element=element;
	}
	
	public boolean isReadOnly(){
		if(element.getAttribute("readonly")==null){
			return false;
		}else{
			return true;
		}
	}
		
	public void check(){
		if(!element.isSelected()){
			element.click();
		}
	}
	
	public void scrollDownAndCheck(){
		if(!element.isSelected()){
			element.scrollDownAndClick();
		}
	}
	
	public void scrollUpAndCheck(){
		if(!element.isSelected()){
			element.scrollUpAndClick();
		}
	}
	
	public void uncheck(){
		if(element.isSelected()){
			element.click();
		}
	}
	
	public void scrollUpAndUncheck(){
		if(element.isSelected()){
			element.scrollUpAndClick();
		}
	}
	
	public void scrollDownAndUncheck(){
		if(element.isSelected()){
			element.scrollDownAndClick();
		}
	}
}
