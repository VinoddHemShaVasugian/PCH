package com.pch.survey.pages.profiles;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class HandraiserPage extends PageObject {

	private By letsGoButton = By.linkText("LET'S GO");
	private By registerButton = By.linkText("Register");
	private By signInButton = By.linkText("Sign In");

	public HandraiserPage(WebDriver driver) {
		super(driver);
	}

	public void clickProfileBuilderLetsGoButton() {
		waitUntilUrlContains("pchsurveys");

		driver.findElement(letsGoButton).click();
	}

	public void clickRegisterButton() {
		driver.findElement(registerButton).click();
	}

	public void clickSignInButton() {
		driver.findElement(signInButton).click();
	}

	public void clickTakeSurvey() {
		waitUntilUrlContains("pchsurveys");
		driver.findElement(By.linkText("TAKE SURVEY")).click();
	}

	public WebElement PrivacyPolicyCloseButton() {
		By PrivacyPolicyClose = By.xpath("//div[@class='close_btn_thick']");
		List<WebElement> elements = driver.findElements(PrivacyPolicyClose);
		if (elements.size() > 0)
			return elements.get(0);
		else
			return null;
	}

	public void clickPrivacyPolicyCloseButton() {

		try {
			WebElement ele = PrivacyPolicyCloseButton();
			if (ele != null)
				ele.click();
		} catch (Exception exception) {
		}
	}

}
