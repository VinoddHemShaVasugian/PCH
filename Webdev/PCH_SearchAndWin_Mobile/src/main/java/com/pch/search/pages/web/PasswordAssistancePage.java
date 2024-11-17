package com.pch.search.pages.web;

import java.util.Date;

import org.openqa.selenium.By;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.HtmlElement;

public class PasswordAssistancePage extends Action {

	public void enterEmail(String email){
		driver.findTextBox(By.id("EM")).setText(email);
	}
	
	/**
	 * 
	 * @return Date Time at which click happened
	 */
	public Date clickSubmit(){
		HtmlElement submitButon = driver.findElement(By.id("miniforgotpass-sub-btn")); 
		submitButon.click();
		Date d = new Date();
		
		driver.waitForBrowserToLoadCompletely();
		return d;
	}
}
