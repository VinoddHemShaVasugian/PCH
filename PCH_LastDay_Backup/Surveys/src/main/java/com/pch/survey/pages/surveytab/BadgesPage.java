package com.pch.survey.pages.surveytab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.survey.pages.PageObject;

public class BadgesPage extends PageObject {

	private By backToSurveysLink = By.linkText("Back To Surveys");
	private By programTermsLink = By.linkText("Program Terms");
	private By infoIcon = By.cssSelector("svg.badge-container__toggle");
	private By infoText = By.cssSelector("div.badge-container__hover-container div");

	public BadgesPage(WebDriver driver) {
		super(driver);
	}

	public void backToSurveysLink() {
		driver.findElement(backToSurveysLink).click();
	}

	public void programTermsLink() {
		driver.findElement(programTermsLink).click();
	}

	public void clickInfoIcon(String badgeName) {
		driver.findElement(By.xpath("//*[text()='" + badgeName + "']/parent::div[@class='badge-container']"))
				.findElements(infoIcon).get(0).click();
	}

	public String getBadgeImgSrc(String badgeName) {
		return driver.findElement(By.xpath("//*[text()='" + badgeName + "']/parent::div[@class='badge-container']/img"))
				.getAttribute("src");
	}

	public String getBadgeInfoText(String badgeName) {
		return driver.findElement(By.xpath("//*[text()='" + badgeName + "']/parent::div[@class='badge-container']"))
				.findElement(infoText).getText();
	}

}