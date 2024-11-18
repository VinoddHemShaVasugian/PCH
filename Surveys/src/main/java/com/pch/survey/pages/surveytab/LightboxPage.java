package com.pch.survey.pages.surveytab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class LightboxPage extends PageObject {

	private By onsiteMsgCloseButton = By.id("onsite-messaging_close");
	private By onsiteMsgSurveyButton = By.cssSelector("button.onsite-messaging__button--cta");
	private By onsiteMsgSubTitle = By.cssSelector("h3.onsite-messaging__text__sub-title");
	private By onsiteMsg = By.cssSelector("div.onsite-messaging__container");
	private By closeBonusGameLB = By.cssSelector(".modal__close");

	public LightboxPage(WebDriver driver) {
		super(driver);
	}

	public LightboxPage() {
		super();
	}

	public void closeBonusGameLB() {
		try {
			waitUntilElementIsVisible(20, closeBonusGameLB);
			driver.findElement(closeBonusGameLB).click();
		} catch (Exception e) {
		}
	}

	public boolean verifySurveyMsgPopupDisplayed() {
		waitUntilThePageLoads();
		return driver.findElement(onsiteMsg).isDisplayed();
	}

	public void clickSurveyButton() {
		driver.findElement(onsiteMsgSurveyButton).click();
	}

	public void closeSurveyMsgPopup() {
		driver.findElement(onsiteMsgCloseButton).click();
	}

	public boolean verifySubTitleFromOnsiteMsg(String eventType) {
		String subTitle = driver.findElement(onsiteMsgSubTitle).getText();
		if (subTitle.contains(eventType))
			return true;
		else
			return false;
	}

	public void closeStackTheRewards() {

		waitUntilElementIsVisible(10, By.xpath("//img[@alt='Skip this opportunity']"));
		WebElement element = driver.findElement(By.xpath("//img[@alt='Skip this opportunity']"));

		waitUntilElementIsClickable(element);

		element.click();
	}
}