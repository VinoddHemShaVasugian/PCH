package com.pch.survey.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.utilities.ConfigurationReader;

public class CommonHeadersAndFooters extends PageObject {

	private static String deviceType = ConfigurationReader.getInstance().getDeviceType().toUpperCase();

	private By pchSurveysLogo = By.className("uninav__logo");

	public CommonHeadersAndFooters(WebDriver driver) {
		super(driver);
	}

	public CommonHeadersAndFooters() {
		super();
	}
 
	public boolean isPchSurveysMainLogoDisplayed() {
		WebElement logo = driver.findElement(pchSurveysLogo);
		if (logo.getAttribute("href").contains("pch.com/pchsurveys")
				&& logo.getAttribute("title").equalsIgnoreCase("PCHsurveys")
				&& logo.getAttribute("style").contains("/uninav/dist/images/uninav/logos/pchsurveys.png"))
			return true;
		return false;
	}

	public void clickMenuIcon(String linkText) {
		WebElement menuItem = waitUntilElementIsClickable(driver.findElement(By.linkText(linkText)));
		menuItem.click();
		waitSeconds(1);
	}

	public boolean isMenuIconDisplayed(String linkText) {
		
		// To explicitly wait for an element upon timeout seconds
		waitUntilElementIsVisible(10, By.linkText(linkText));
 		
		WebElement menuItem = driver.findElement(By.linkText(linkText));
		return true;
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public boolean getPageTitle(String title) {
		return waitUntilPageTitleContains(title);
	}

 
	
	
	
	
	
	
	
	
	
	// MOBLIE

	public void clickHamburgerMenu() {
		driver.findElement(By.cssSelector(".uninav__burger")).click();
	}

	
	
	
	
	
	
	
}
