package com.pch.survey.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.survey.pages.PageObject;

 
public class SignInPage extends PageObject {
 
	private By email = By.id("u_em");
	private By password = By.id("password_main");
	private By submit = By.cssSelector("input[type=\"submit\"]");

	
	
	public SignInPage(WebDriver driver) {
		super(driver);
	}
 
   
	
	public void typeEmail(String val){
		waitUntilThePageLoads();
		waitUntilUrlContains("login");
		driver.findElement(email).sendKeys(val);;
	}

	
	
	public void typePassword(String val){
		driver.findElement(password).sendKeys(val);;
	}
	
	public void clickSignIn(){
		driver.findElement(submit).click();
	}

	
}
