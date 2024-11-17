package com.pch.survey.pages.surveytab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.PageObject;

public class SurveyCompletePage extends PageObject {

	private By continueBtn = By.id("continue");
	private By passwordTxt = By.id("pw");
	private By confirmPasswordTxt = By.id("confirm");
	private By submitBtn = By.id("pwbutton");
	private By noThanks = By.id("no-thanks");

	public SurveyCompletePage(WebDriver driver) {
		super(driver);
	}

	public SurveyCompletePage() {
		super();
	}

	public void completeSurvey() {
		waitUntilUrlContains("survey");
		WebElement ele = driver.findElement(continueBtn);
		scrollIntoView(ele);
		ele.click();
	}

	public void completeSurveyForSilverUser() {
		waitUntilUrlContains("surveycomplete");
		WebElement password = driver.findElement(passwordTxt);
		scrollIntoView(password).sendKeys("pch123");
		driver.findElement(confirmPasswordTxt).sendKeys("pch123");
		WebElement ele = driver.findElement(submitBtn);
		scrollIntoView(ele);
		ele.click();
		waitSeconds(10);
		try {
			if (driver.findElement(continueBtn).isDisplayed())
				driver.findElement(continueBtn).click();
		} catch (Exception e) {

		}
	}

	public void completeSurveyAndSkipForSilverUser() {
		waitUntilUrlContains("surveycomplete");
		WebElement ele = driver.findElement(noThanks);
		scrollIntoView(ele).click();
	}
}