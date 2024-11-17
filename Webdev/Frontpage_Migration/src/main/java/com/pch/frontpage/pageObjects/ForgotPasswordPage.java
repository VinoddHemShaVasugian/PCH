package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class ForgotPasswordPage extends BaseClass {

	private static final ForgotPasswordPage instance = new ForgotPasswordPage();

	private ForgotPasswordPage() {
	}

	public static ForgotPasswordPage getInstance() {
		return instance;
	}

	private final By forgotPassword_clickhere = By.linkText("Click Here");
	private final By forgotPassword_email = By.id("EM");
	private final By forgotPassword_submit = By.id("miniforgotpass-sub-btn");
	private final By forgotPassword_confirmationMsg = By.id("pwdr_msg");

	private final By errorMessage = By.cssSelector("li");

	/**
	 * To verify the unrec email message on Forgot Password page
	 */
	public boolean verify_forgotPassword_UnrecEmail(String verficatontext) {
		button(forgotPassword_clickhere, 10);
		textbox(forgotPassword_email, "enter", ("FP" + randomString(5, 6) + Date() + "@pchmail.com"), 10);
		button(forgotPassword_submit, 10);
		waitForElement(errorMessage, 10);
		sleepFor(3);
		return getText(errorMessage, 15).equals(verficatontext);
	}

	/**
	 * To verify the forgot Password functionality
	 * 
	 * @param verficatontext
	 * @return boolean
	 */
	public boolean verify_forgotPassword(String verficatontext) {
		button(forgotPassword_clickhere, 10);
		textbox(forgotPassword_email, "enter", xmlReader(ENVIRONMENT, "fp_email"), 10);
		button(forgotPassword_submit, 10);
		waitForElement(forgotPassword_confirmationMsg, 10);
		sleepFor(3);
		return getText(forgotPassword_confirmationMsg, 15).equals(verficatontext);
	}

}
