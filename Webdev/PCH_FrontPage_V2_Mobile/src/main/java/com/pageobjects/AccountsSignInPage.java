package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class AccountsSignInPage extends BaseClass {

	private static final AccountsSignInPage instance = new AccountsSignInPage();

	private AccountsSignInPage() {
	}

	public static AccountsSignInPage getInstance() {
		return instance;
	}

	private final By email = By.name("email");
	private final By password = By.name("password");
	private final By login_btn = By.cssSelector("button.sign-in-btn");
	private final By errormessage = By.cssSelector("li");
	private final By register = By.xpath("//div[@class='register-link']/a[text()='Register']");
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();

	/**
	 * Click on Register button
	 */
	public void click_register() {
		button(register, 10);
	}

	/**
	 * To validate the error pages
	 */
	public boolean verify_errormessage(String verificationtext) {
		waitForElementUntilTextPresent(errormessage, verificationtext, 10);
		return getText(errormessage, 10).equals(verificationtext);
	}

	/**
	 * To Login to the site
	 */
	public void login(String user_email, String user_password) {
		textbox(email, "enter", user_email, 10);
		textbox(password, "enter", user_password, 10);
		button(login_btn, 10);
		lb_instance.close_welcome_optin_lb();
	}

	/**
	 * This method can be used to login by only passing password. Can be used
	 * when session timeout happens
	 */
	public void login(String user_password) {
		textbox(password, "enter", user_password, 10);
		button(login_btn, 10);
	}

	/**
	 * To Login to the site
	 */
	public void login_without_close_optin(String user_email, String user_password) {
		textbox(email, "enter", user_email, 10);
		textbox(password, "enter", user_password, 10);
		button(login_btn, 10);
	}

}
