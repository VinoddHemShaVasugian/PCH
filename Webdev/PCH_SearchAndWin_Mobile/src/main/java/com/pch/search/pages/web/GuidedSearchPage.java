package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class GuidedSearchPage extends Action{

	private By guidedSearchSection=By.id("guided-search");

	private HtmlElement GuidedSearchTitle(){
		return driver.findElement(By.xpath(".//section[@id='gs-blocks']/div[1]/div[1]"));
	}	

	/**
	 * This function will check if guided search grid exists
	 * @return True if exists false otherwise
	 * */
	public boolean isGuidedSearchSectionDisplayed(){
		boolean isDisplayed=false;
		try{
			driver.findElement(guidedSearchSection);
			isDisplayed=true;
		}catch(NoSuchElementException ex){
			CustomLogger.log("Guided Search does not display");
		}
		return isDisplayed;
	}
	/**
	 * This is to click on any search term(first)
	 * */
	public void clickGuidedSearch(){	
		Common.sleepFor(2000);
		GuidedSearchTitle().click();
	}
	/**
	 *This is to click on search term using its title
	 **/
	public void clickGuidedSearchByTitle(String title){		
		driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]", title))).click();
	}
	public String getGuidedSearchTitle(){		
		return GuidedSearchTitle().getAttribute("data-title");
	}
	/**
	 * This is to get all the guided search terms
	 * @return List of search terms
	 * */
	public List<String> getGuidedSearchTitles(){	
		List<String> AllSearchTerms=new ArrayList<String>();
		List<HtmlElement> searchTerms=driver.findElements(By.xpath(".//*[contains(@class,'grid-item gs-block')]"));
		for(HtmlElement searchTerm:searchTerms){
			AllSearchTerms.add(searchTerm.findElement(By.className("gs-block-title")).getAttribute("data-title"));
		}
		return AllSearchTerms;
	}
}
