package com.pch.search.iwe;

import org.openqa.selenium.By;

import com.pch.search.utilities.Common;
import com.pch.search.utilities.HtmlElement;

public class AllPlaysPage extends IWEBasePage{

	public HtmlElement searchByEmail(){
		return driver.findElement(By.name("email"));
	}

	public HtmlElement searchAllPlaysBtn(){
		return driver.findElement(By.xpath("//span[text()='Search all Plays']"));
	}

	public HtmlElement resetFiltersBtn(){
		return driver.findElement(By.xpath("//span[text()='Reset Filters']"));
	}

	public void searchAllPlays(String userEmail){
		resetFiltersBtn().click();
		searchByEmail().sendKeys(userEmail);
		searchAllPlaysBtn().click();
		Common.sleepFor(5000);
	}

	public int getDeviceCount(String deviceName){
		return driver.getCountOfElementsWithXPath(String.format("//div[text()='%s']",deviceName));
	}
}
