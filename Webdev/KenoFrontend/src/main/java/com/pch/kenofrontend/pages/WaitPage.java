package com.pch.kenofrontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.serenitybdd.core.pages.PageObject;

public class WaitPage extends PageObject{

	public WaitPage(WebDriver driver) {
		super(driver);
	}
	
	public WaitPage() {
		
	}

	public void waitForDomLoad(){
		waitForLoad(getDriver());
	}

	public void waitForLoadingMaskDisappear(){
		forcibleWait(2000);
		waitForRenderedElementsToDisappear(By.xpath("//div[@class='k-loading-mask']"));
	}

	void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}
	
	public void waitForElementPresent(By by){
		waitFor(ExpectedConditions
				.presenceOfElementLocated(by));
	}

	public void forcibleWait(int duration){
		try{
			waitABit(duration);
		}catch(RuntimeException re){
			waitABit(duration);
		}
	}

}
