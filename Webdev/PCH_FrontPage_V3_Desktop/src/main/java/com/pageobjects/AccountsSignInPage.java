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

	private final By email = By.id("EM");
	private final By password = By.id("PW");
	private final By login_btn = By.id("login-sub-btn");
	private final By log_out_link = By.linkText("Sign Out");
	private final By errormessage = By.cssSelector("li");
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();

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
	 * This method can be used to login by only passing password. Can be used when
	 * session timeout happens
	 */
	public void login(String user_password) {
		textbox(password, "enter", user_password, 10);
		button(login_btn, 10);
	}

	/**
	 * To Logout from the site
	 */
	public void logout() {
		button(log_out_link, 5);
		waitForElementToBePresent(login_btn, 5);
	}

	public boolean logout_element_presence() {
		return elementPresent(log_out_link);
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
