package com.pch.automation.pages;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class SignInPage extends PageObject {

	private final By email = By.id("EM");
	private final By password = By.id("PW");
	private final By loginBtn = By.id("login-sub-btn");

	/**
	 * Login to the site
	 */
	public void login(String userEmail, String userPassword) {
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