package com.pch.search.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class SelectList {
	private Select select;

	protected SelectList(Select select){
		this.select=select;
	}

	public void selectByVisibleText(String... texts){
		for(String txt:texts){
			select.selectByVisibleText(txt);
		}
	}

	public void selectByIndex(Integer... indexes){
		for(Integer indx:indexes)
			select.selectByIndex(indx);
	}

	public void selectByValue(String... values){
		for(String value:values){
			select.selectByValue(value);
		}
	}

	public void deselectByVisibleText(String text){
		select.deselectByVisibleText(text);
	}

	public void deselectByIndex(int index){
		select.deselectByIndex(index);
	}

	public void deselectAll(){
		select.deselectAll();
	}


	public String getFirstSelectedOption(){
		return select.getFirstSelectedOption().getText();
	}

	public List<String> getAvailableOptions(){
		List<String> options = new ArrayList<String>();
		for(WebElement option_Element:select.getOptions()){
			options.add(option_Element.getText());
		}
		return options;
	}

	public List<String> getAllSelectedOptions(){
		List<String> options = new ArrayList<String>();
		for(WebElement option_Element:select.getAllSelectedOptions()){
			options.add(option_Element.getText());
		}
		return options;
	}

	public boolean areValuesPresent(String... values){
		List<String> availableOptions =getAvailableOptions();
		return availableOptions.containsAll(Arrays.asList(values));
	}

	public boolean areValuesSelected(String... values){
		List<String> selectedOptions = getAllSelectedOptions();
		return selectedOptions.containsAll(Arrays.asList(values));
	}

	public Select getWrappedSelectElement(){
		return select;
	}


}
