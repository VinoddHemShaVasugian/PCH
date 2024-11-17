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
	
	String iframXpath = ".//iframe[@class='guidedSearchFrame']";

	private By guidedSearchSection=By.xpath(".//*[@class='container-fluid']");

	private HtmlElement GuidedSearchTitle(){
		String layout10 = "html/body/div[1]/div/section/article[3]/div/div/a[1]/span";
		String layout2 = "html/body/div[1]/div/section/article[3]/div/div/section/a[1]/span";
		driver.switchTo().defaultContent();
		driver.switchTo_iframe(iframXpath);
		
		if(driver.getCountOfElementsWithXPath(layout2)>0){
			return driver.findElement(By.xpath(layout2));
		}else if(driver.getCountOfElementsWithXPath(layout10)>0) {
			return driver.findElement(By.xpath(layout10));
		}
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
		Common.sleepFor(3000);
	}
	/**
	 *This is to click on search term using its title
	 **/
	public void clickGuidedSearchByTitle(String title){		
		driver.findElement(By.xpath(String.format("//span[contains(text(),'%s')]", title))).click();
	}
	public String getGuidedSearchTitle(){		
		return GuidedSearchTitle().getText();/*getAttribute("data-title");*/
	}
	/**
	 * This is to get all the guided search terms
	 * @return List of search terms
	 * */
	public List<String> getGuidedSearchTitles(){
		String gslayoutSearchTerms = "html/body/div[1]/div/section/article[3]/div/div/section/a";
		String gsSearchesXpath = "html/body/div[1]/div/section/article[3]/div/div/section/a/span";
		List<String> AllSearchTerms=new ArrayList<String>();
		driver.switchTo().defaultContent();
		driver.switchTo_iframe(iframXpath);
		CustomLogger.log("Elements : "+driver.findElements(By.xpath(gslayoutSearchTerms)));
		List<HtmlElement> searchTerms=driver.findElements(By.xpath(gslayoutSearchTerms));/*".//*[contains(@class,'grid-item gs-block')]"));*/
		for(HtmlElement searchTerm:searchTerms){
			AllSearchTerms.add(searchTerm.findElement(By.xpath("span")).getText());
		}
		return AllSearchTerms;
	}
}
