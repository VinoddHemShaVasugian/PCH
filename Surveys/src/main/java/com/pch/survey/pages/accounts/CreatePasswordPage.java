package com.pch.survey.pages.accounts;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class CreatePasswordPage extends PageObject {

	private By userName = By.cssSelector(".body-welcome");
	private By continueBtn = By.id("continue");
	private By passwordTxt = By.id("pw");
	private By confirmPasswordTxt = By.id("confirm");
	private By submitBtn = By.id("pwbutton");
//	private By noThanks = By.id("no-thanks");
//	private By policy = By.cssSelector(".policy");
	private By phpdebugbarClose = By.cssSelector(".phpdebugbar-close-btn");
	private By lbPasswordTxt = By.cssSelector(".lbpassword");
	private By lbConfirmPasswordTxt = By.cssSelector(".lbconfirmPassword");
	private By lbSubmitBtn = By.cssSelector(".submit");

	public CreatePasswordPage(WebDriver driver) {
		super(driver);
	}

	public CreatePasswordPage() {
		super();
	}

	public String getUserName() {
		waitUntilElementIsVisible(5, userName);
		return driver.findElement(userName).getText();
	}

	public void createPasswordLb() {
		waitUntilElementIsVisible(10, lbPasswordTxt);
		WebElement password = driver.findElement(lbPasswordTxt);
		scrollIntoView(password).sendKeys("pch123");
		driver.findElement(lbConfirmPasswordTxt).sendKeys("pch123");
		try {
			WebElement ele = driver.findElement(lbSubmitBtn);
			scrollIntoView(ele);
			ele.click();
		} catch (ElementClickInterceptedException eci) {
			driver.findElement(phpdebugbarClose).click();
			WebElement ele = driver.findElement(lbSubmitBtn);
			ele.click();

		}
		waitSeconds(5);
		try {
			if (driver.findElement(continueBtn).isDisplayed())
				driver.findElement(continueBtn).click();
		} catch (Exception e) {

		}
	}

	public void completeSurveyForSilverUser() {
		waitUntilThePageLoads();
		waitSeconds(5);
		waitUntilUrlContains("create-password");
		WebElement password = driver.findElement(passwordTxt);
		scrollIntoView(password).sendKeys("pch123");
		driver.findElement(confirmPasswordTxt).sendKeys("pch123");
		try {
			WebElement ele = driver.findElement(submitBtn);
			scrollIntoView(ele);
			ele.click();
		} catch (ElementClickInterceptedException eci) {
			driver.findElement(phpdebugbarClose).click();
			WebElement ele = driver.findElement(submitBtn);
			ele.click();

		}
		waitSeconds(5);
		try {
			if (driver.findElement(continueBtn).isDisplayed())
				driver.findElement(continueBtn).click();
		} catch (Exception e) {

		}
	}

}
