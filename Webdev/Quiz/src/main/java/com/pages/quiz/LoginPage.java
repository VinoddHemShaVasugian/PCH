package com.pages.quiz;

import org.openqa.selenium.By;

import com.pch.quiz.utilities.AppConfigLoader;

import net.serenitybdd.core.pages.PageObject;

/**
 * @author vsankar Contains page functions for the quiz Joomla login page
 * 
 */
public class LoginPage extends PageObject {

	private final By username = By.id("mod-login-username");
	private final By password = By.id("mod-login-password");
	private final By loginForm = By.id("form-login");
	private final By joomlaIcon = By.cssSelector("span.icon-joomla");
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	/**
	 * Enter the UserName
	 * 
	 * @param userName
	 */
	private void enterUsername(String userName) {
		typeInto(element(username), userName);
	}

	/**
	 * Enter the Password
	 * 
	 * @param userName
	 */
	private void enterPassword(String pwd) {
		typeInto(element(password), pwd);
	}

	/**
	 * Submit the login form
	 * 
	 * @param userName
	 */
	private void submitLoginForm() {
		element(loginForm).submit();
	}

	/**
	 * Login to the Quiz Joomla admin
	 * 
	 * @param username
	 * @param password
	 */
	public void loginQuizAdmin() {
		this.openJoomlaApplication();
		this.enterUsername(configInstance.getEnvironmentProperty("JoomlaUserName"));
		this.enterPassword(configInstance.getEnvironmentProperty("JoomlaPassword"));
		this.submitLoginForm();
		waitForRenderedElementsToBePresent(joomlaIcon);
		clickOn(element(joomlaIcon));
	}

	/**
	 * Open the Joomla admin application
	 */
	public void openJoomlaApplication() {
		if (System.getProperty("testing.DeviceType") != null
				&& System.getProperty("testing.DeviceType").equalsIgnoreCase("Desktop")) {
			getDriver().manage().window().maximize();
		}
		getDriver().manage().deleteAllCookies();
		getDriver().get(configInstance.getEnvironmentProperty("JoomlaUrl"));
	}
}
