package com.pch.survey.pages.accounts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;
import com.pch.survey.utilities.RandomDataGenerator;

public class SurveyRegistrationPage extends PageObject {

	private By title = By.cssSelector(".title");
	private By firstName = By.cssSelector(".first-name");
	private By lastName = By.cssSelector(".last-name");
	private By street = By.cssSelector(".street");
	private By apt = By.cssSelector(".apt");
	private By city = By.cssSelector(".city");
	private By state = By.cssSelector(".state");
	private By zip = By.cssSelector(".zip");
	private By month = By.cssSelector(".month");
	private By day = By.cssSelector(".day");
	private By year = By.cssSelector(".year");
	private By email = By.cssSelector(".email");
	private By confirmEmail = By.cssSelector(".confirm-email");
	private By submitButton = By.cssSelector(".submit-button");

	public SurveyRegistrationPage(WebDriver driver) {
		super(driver);
	}

	public void selectTitle(String option) {
		waitUntilThePageLoads();
		WebElement userTitle = driver.findElement(title);
		waitUntilElementIsVisible(30, title);
		selectFromDropdown(userTitle, option);
	}

	public void typeFirstName(String text) {
		driver.findElement(firstName).sendKeys(text);
	}

	public void typeLastName(String text) {
		driver.findElement(lastName).sendKeys(text);
	}

	public void typeStreetAddress(String text) {
		driver.findElement(street).sendKeys(text);
	}

	public void typeStreetApt(String text) {
		driver.findElement(apt).sendKeys(text);
	}

	public void typeZip(String text) {
		driver.findElement(zip).sendKeys(text);
	}

	public void typeEmail(String text) {
		driver.findElement(email).sendKeys(text);
	}

	public void typeCity(String text) {
		driver.findElement(city).sendKeys(text);
	}

	public void typeConfirmEmail(String text) {
		driver.findElement(confirmEmail).sendKeys(text);
	}

	public void selectState(String option) {
		selectFromDropdown(driver.findElement(state), option);
	}

	public void selectMonthOfBirth(String option) {
		selectFromDropdown(driver.findElement(month), option);
	}

	public void selectDayOfBirth(String option) {
		selectFromDropdown(driver.findElement(day), option);
	}

	public void selectYearOfBirth(String option) {
		selectFromDropdown(driver.findElement(year), option);
	}

	public void clickSubmitButton() {
		waitSeconds(5);
		WebElement submitBtn = driver.findElement(submitButton);
		scrollIntoView(submitBtn);
		waitUntilElementIsClickable(submitBtn);
		submitBtn.click();
	}

	/**
	 * Create the silver User
	 */
	public void createSilverUser() {
		selectTitle("Mr.");
		typeFirstName("QA_Auto");
		typeLastName("SurveysTab");
		typeStreetAddress("300 Jericho Quadrangle");
		typeStreetApt("Dept 300");
		typeCity("Jericho");
		selectState("New York");
		typeZip("11753");
		selectMonthOfBirth("May");
		selectDayOfBirth("12");
		selectYearOfBirth("1999");
		String email = RandomDataGenerator.getRandomEmail();
		typeEmail(email);
		typeConfirmEmail(email);
		clickSubmitButton();
		waitSeconds(5);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}

}