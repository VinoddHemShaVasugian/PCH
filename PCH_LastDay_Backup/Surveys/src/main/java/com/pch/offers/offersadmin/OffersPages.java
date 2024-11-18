package com.pch.offers.offersadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class OffersPages extends PageObject {

	private By email = By.id("email");
	private By password = By.id("password");
	private By submit = By.xpath("//button[@type='submit']");
	private By phpdebugbarClose = By.cssSelector(".phpdebugbar-close-btn");

	public OffersPages(WebDriver driver) {
		super(driver);
	}

	public OffersPages() {
		super();
	}

	public void loginOffersAdmin(String offersAdminUserName, String offersAdminPassword) {
		waitUntilThePageLoads();
		waitUntilUrlContains("login");
		try {
			driver.findElement(phpdebugbarClose).click();
		} catch (Exception e1) {
			System.out.println("phpdebugbar is invisible");
		}
		WebElement userName = driver.findElement(email);
		WebElement pwd = driver.findElement(password);
		userName.sendKeys(offersAdminUserName);
		pwd.sendKeys(offersAdminPassword);
		try {
			WebElement ele = driver.findElement(submit);
			waitUntilElementIsClickable(ele);
			scrollIntoView(ele);
			ele.click();
		} catch (Exception e) {
			userName.clear();
			pwd.clear();
			userName.sendKeys(offersAdminUserName);
			pwd.sendKeys(offersAdminPassword);
			driver.findElement(submit).sendKeys(Keys.ENTER);
		}
	}
}