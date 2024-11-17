package com.pch.survey.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.survey.pages.PageObject;

 
public class CreatePasswordPage extends PageObject {
 
	private By userName = By.cssSelector(".body-welcome");

	
	
	public CreatePasswordPage(WebDriver driver) {
		super(driver);
	}
	public CreatePasswordPage() {
		super();
	}

	public String  getUserName(){
		waitUntilElementIsVisible(5,userName);
		return driver.findElement(userName).getText();
	}

	
}
