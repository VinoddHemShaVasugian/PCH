package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public abstract class LightBox {
	protected BrowserDriver driver;

	protected abstract HtmlElement locateLightBox();

	public abstract void dismissLightBox();

	public boolean isLightBoxPresent() {
		HtmlElement lightBox = null;
		try {
			lightBox = locateLightBox();
			if (lightBox.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public boolean isTextDisplayed(String displayedText) {
		String xpath = String.format("//*[contains(text(),\"%s\")]",
				displayedText);
		try {
			if (locateLightBox().getCountOfElementsWithXPath(xpath) == 0) {
				return false;
			}
			HtmlElement e = locateLightBox().findElement(By.xpath(xpath));
			return e.isDisplayed();
		} catch (WebDriverException e) {
			CustomLogger.logException(e);
			return false;
		}
	}
	
	private HtmlElement searchOptIn(){
		return driver.findElement(By.id("stylish-optin-site"));
	}
	
	private HtmlElement pchOptIn(){
		return driver.findElement(By.id("stylish-optin-pchcom"));
	}
	
	private HtmlElement submitButton(){
		return driver.findElement(By.id("optin-submit"));
	}
	
	public boolean isSearchOptinPresent() {
		boolean present;
		try {
			searchOptIn();
		   present = true;
		} catch (NoSuchElementException e) {
		   present = false;
		}
		return present;
	}
	
	public boolean isPchOptinPresent() {
		boolean present;
		try {
			pchOptIn();
			present = true;
		} catch (NoSuchElementException e) {
		   present = false;
		}
		return present;
	}
	
	public void checkSearchOptin(){
		searchOptIn().click();
		CustomLogger.log("Check Search & Win OptIn.");
	}
	
	public void checkPchOptin(){
		pchOptIn().click();
		CustomLogger.log("Check PCHCom OptIn.");
	}
	
	public void clickSubmitButton(){
		submitButton().click();
		CustomLogger.log("Clicked on Submit Button.");
	}
	
}
