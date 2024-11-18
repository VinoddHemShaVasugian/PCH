package com.pch.survey.pages.profiles;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.survey.pages.PageObject;

public class LucidConsentFormPage extends PageObject {

	public LucidConsentFormPage(WebDriver driver) {
		super(driver);
	}
	public LucidConsentFormPage() {
		super();
	}

	public void agreeLucentConsentForm() {
		waitUntilThePageLoads();
		driver.findElement(By.cssSelector("#option-0")).click();
		driver.findElement(By.cssSelector(".submit-btn")).click();
		
		}
	
	
	
	
	
}
