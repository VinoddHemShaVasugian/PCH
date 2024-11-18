package com.pch.survey.pages.accounts;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.PageObject;
import com.pch.survey.utilities.RandomDataGenerator;

public class MpoRegistrationPage extends PageObject {

	private By title = By.id("input-title");
	private By firstName = By.id("input-first_name");
	private By lastName = By.id("input-last_name");
	private By street = By.id("input-address");
	private By apt = By.id("input-address2");
	private By zip = By.id("input-zip");
	private By email = By.id("input-email");
	private By confirmEmail = By.id("input-confirm_email");
	private By birth_month = By.id("input-birth_month");
	private By birth_day = By.id("input-birth_day");
	private By birth_year = By.id("input-birth_year");
	private By password = By.cssSelector("#input-password");
	private By confirmPassword = By.cssSelector("#input-confirm_password");
	private By continueButton = By.cssSelector(".btn--continue");
	private By signInButton = By.cssSelector(".btn--login");
	private By submitButton = By.xpath("//button[@class='submit-button']");

	public MpoRegistrationPage(WebDriver driver) {
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

	public void typeConfirmEmail(String text) {
		driver.findElement(confirmEmail).sendKeys(text);
	}

	public void selectMonthOfBirth(String option) {
		selectFromDropdown(driver.findElement(birth_month), option);
	}

	public void selectDayOfBirth(String option) {
		selectFromDropdown(driver.findElement(birth_day), option);
	}

	public void selectYearOfBirth(String option) {
		selectFromDropdown(driver.findElement(birth_year), option);
	}

	public void typePassword(String text) {
		driver.findElement(password).sendKeys(text);
	}

	public void typeConfirmPassword(String text) {
		driver.findElement(confirmPassword).sendKeys(text);
	}

	public void clickSubmitButton() {
		waitSeconds(5);
		WebElement submitBtn = driver.findElement(continueButton);
		scrollIntoView(submitBtn);
		waitUntilElementIsClickable(submitBtn);
		submitBtn.click();
	}

	public void CreateFullRegUserForm() {
		selectTitle("Mr.");
		typeFirstName("QA_Auto");
		typeLastName("SurveysTab");
		typeStreetAddress("300 Jericho Quadrangle");
		typeStreetApt("Dept 300");
		typeZip("11753");
		String email = RandomDataGenerator.getRandomEmail();
		typeEmail(email);
		typeConfirmEmail(email);
		selectMonthOfBirth("May");
		selectDayOfBirth("12");
		selectYearOfBirth("1999");
		typePassword("pch123");
		typeConfirmPassword("pch123");
		Registrations.setEmail(email);
		clickSubmitButton();
		waitSeconds(10);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}

	/**
	 * Complete the Mini Reg User
	 */
	public void completeMiniRegUser() {
		selectTitle("Mr.");
		typeFirstName("QA_Auto");
		typeLastName("SurveysTab");
		typeStreetAddress("300 Jericho Quadrangle");
		typeStreetApt("Dept 300");
		typeZip("11753");
		selectMonthOfBirth("May");
		selectDayOfBirth("12");
		selectYearOfBirth("1999");
		if (driver.findElement(password).isDisplayed()) {
			driver.findElement(password).sendKeys(Registrations.getPassword());
			driver.findElement(confirmPassword).sendKeys(Registrations.getPassword());
		}
		clickSubmitButton();
		waitSeconds(5);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		try {
			if (driver.findElement(password).isDisplayed()) {
				driver.findElement(password).sendKeys(Registrations.getPassword());
				driver.findElement(signInButton).click();
			}
		} catch (Exception e) {

		}
		try {
			waitUntilThePageLoads();
			clickSubmitButton();
		} catch (Exception e) {
		}
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
		typeZip("11753");
		selectMonthOfBirth("May");
		selectDayOfBirth("12");
		selectYearOfBirth("1999");
		String email = RandomDataGenerator.getRandomEmail();
		typeEmail(email);
		typeConfirmEmail(email);
		driver.findElement(submitButton).click();
		waitSeconds(5);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}

}