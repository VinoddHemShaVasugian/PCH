package com.pch.offers.offersadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.pages.PageObject;

public class SurveyEndToolPages extends PageObject {

	private static String env = ConfigurationReader.getEnvironment();

	private By exampleUrl = By.id("example-url");
	private By selectEnvironment = By.id("environment");
	private By prequals = By.id("prequalsKey");
	private By surveyComplete = By.id("complete");
	private By surveyIncomplete = By.id("incomplete");
	private By submit = By.xpath("//*[contains(text(),'Submit')]");
	private By surveyEndUrl = By.cssSelector("dd.mt-1");

	public SurveyEndToolPages(WebDriver driver) {
		super(driver);
	}

	public SurveyEndToolPages() {
		super();
	}

	public String generateSurveyCompleteURL(String surveyLandingPageUrl) {
		waitUntilThePageLoads();
		try {
			waitUntilUrlContains("survey-end");
		} catch (Exception e) {
			System.out.println("Survey end tool page is not displayed.");
		}
		waitUntilElementIsVisible(30, exampleUrl);
		driver.findElement(exampleUrl).sendKeys(surveyLandingPageUrl);
		selectFromDropdown(driver.findElement(selectEnvironment), env);
		selectFromDropdown(driver.findElement(prequals), "No Prequals");
		driver.findElement(surveyComplete).click();
		driver.findElement(submit).click();
		waitSeconds(5);
		return driver.findElement(surveyEndUrl).getText();
	}

	public String generateSurveyIncompleteURL(String surveyLandingPageUrl) {
		waitUntilThePageLoads();
		waitUntilUrlContains("survey-end");
		driver.findElement(exampleUrl).sendKeys(surveyLandingPageUrl);
		selectFromDropdown(driver.findElement(selectEnvironment), env);
		selectFromDropdown(driver.findElement(prequals), "No Prequals");
		driver.findElement(surveyIncomplete).click();
		driver.findElement(submit).click();
		waitSeconds(5);
		return driver.findElement(surveyEndUrl).getText();
	}

}
