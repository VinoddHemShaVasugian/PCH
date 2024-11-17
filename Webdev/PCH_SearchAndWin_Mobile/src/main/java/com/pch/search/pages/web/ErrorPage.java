package com.pch.search.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;

public class ErrorPage extends Action{

	public void load(){
		String location = Common.getAppUrl(Environment.getAppName())+"/unknownCategory";
		driver.get(location);
	}

	public boolean is404ErrorDisplayed(){
		try{
			return driver.findElement(By.xpath("//h1[text()='Error 404']")).isDisplayed();
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return false;
		}
	}

	/**
	 * Click on Continue button on Error Page
	 * @return
	 */
	public String clickContinueImage(){		
		driver.findElement(By.xpath("//p[@class='continue']//img")).click();
		driver.waitForBrowserToLoadCompletely();
		return(driver.getCurrentUrl());
	}


	public void clickOnLink(String linkText){
		driver.findElement(By.linkText(linkText)).click();
		CustomLogger.log("Clicked on Link "+linkText);
		driver.waitForBrowserToLoadCompletely();		
	}


}
