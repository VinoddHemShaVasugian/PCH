package com.pages.quiz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;

public class SignInPage extends PageObject {

	/**
	 * Instantiates a SignIn Page.
	 *
	 * @param driver
	 */
	public SignInPage(WebDriver driver) {
		super(driver);
	}

	private final By email = By.id("EM");
	private final By password = By.id("PW");
	private final By loginBtn = By.id("login-sub-btn");
	private final By signIn = By.cssSelector("a.uninav__sign-in");

	/**
	 * Login to the site
	 */
	public void login(String userEmail, String userPassword) {
		if (!isElementVisible(email)) {
			clickOn(element(signIn));
		}
		typeInto(element(email), userEmail);
		typeInto(element(password), userPassword);
		clickOn(element(loginBtn));
	}

	/**
	 * This method can be used to login by only entering password. Can be used when
	 * session timeout happens
	 */
	public void login(String userPassword) {
		typeInto(element(password), userPassword);
		clickOn(element(loginBtn));
	}

	public boolean verifyPasswordField() {
		return isElementVisible(password);
	}
}