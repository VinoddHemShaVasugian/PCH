package com.pch.survey.pages.surveytab;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.pch.survey.pages.PageObject;

public class PersonalAchievementsSectionPage extends PageObject {

	private By achievmentList = By
			.cssSelector("div.survey__card__content__personal-achievements__top__row__displaybox");
	private By badgeHeader = By.className("survey__card__content__personal-achievements__middle__header");
	private By badgeTitle = By.className("survey__card__content__personal-achievements__middle__title");
	private By badgeFooter = By.className("survey__card__content__personal-achievements__middle__footer");
	private By seeAllBadgesLink = By.linkText("See All Badges");
	private By backToSurveysLink = By.linkText("Back To Surveys");
	private By programTermsLink = By.linkText("Program Terms");
	
	

	public PersonalAchievementsSectionPage(WebDriver driver) {
		super(driver);
	}

	public String getSurveysCompletedCount() {
		waitUntilThePageLoads();
		return driver.findElements(achievmentList).get(0).getText();
	}

	public String getBadgesRecievedCount() {
		try {
			return driver.findElements(achievmentList).get(1).getText();
		} catch (StaleElementReferenceException stale) {
			driver.navigate().refresh();
			waitUntilThePageLoads();
			waitSeconds(10);
			return driver.findElements(achievmentList).get(1).getText();
		}
	}

	public String getBadgeHeaderText() {
		waitUntilThePageLoads();
		waitSeconds(30);
		return driver.findElement(badgeHeader).getText();
	}

	public String getBadgeHeaderText(String text) {
		waitUntilThePageLoads();
		waitUntilTextContains(badgeHeader, Pattern.compile(text));
		return driver.findElement(badgeHeader).getText();
	}

	
	
	
	
	public String getBadgeTitleText() {
		return driver.findElement(badgeTitle).getText();
	}

	public String getBadgeFooterText() {
		return driver.findElement(badgeFooter).getText();
	}

	public void clickSeeAllBadgesLink() {
		driver.findElement(seeAllBadgesLink).click();
	}
	
	public void backToSurveysLink() {
		driver.findElement(backToSurveysLink).click();
	}
	
	public void programTermsLink() {
		driver.findElement(programTermsLink).click();
	}
	
	
}