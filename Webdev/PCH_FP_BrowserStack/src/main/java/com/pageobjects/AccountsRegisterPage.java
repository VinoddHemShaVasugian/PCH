package com.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.util.BaseClass;

public class AccountsRegisterPage extends BaseClass {

	private static AccountsRegisterPage instance = new AccountsRegisterPage();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();

	private AccountsRegisterPage() {
	}

	public static AccountsRegisterPage getInstance() {
		return instance;
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By title = By.name("title");
	private final By firstName = By.name("firstname");
	private final By lastName = By.name("lastname");
	private final By street = By.name("street");
	private final By city = By.name("city");
	private final By state = By.name("state");
	private final By zip = By.name("zip");
	private final By mob = By.cssSelector("select.reg-input.month");
	private final By dob = By.cssSelector("select.reg-input.day");
	private final By yob = By.cssSelector("select.reg-input.year");
	private final By email = By.name("email");
	private final By confirmEmail = By.name("confirm-email");
	private final By password = By.name("password");
	private final By confirmPassword = By.name("confirm-password");
	private final By submit = By.id("sub_btn");
	private final By first_optin_checkbox = By.id("optin1");
	private final By second_optin_checkbox = By.id("optin2");
	private final By errorMessageHeader = By.cssSelector("div.header");
	private final By errorMessageContent = By.cssSelector("div.inner-shell");
	private final By password_silveruser = By.cssSelector("input.lbpassword");
	private final By cnfmpassword_silveruser = By.cssSelector("input.lbconfirmPassword");
	private final By submit_silveruser = By.cssSelector("button.submit");
	private final By existing_pwd_confirm_msg = By.cssSelector("div#main_content p.intro:nth-of-type(1)");
	private final By existing_pwd_confirm_button = By.cssSelector("div#sub_btn");

	/**
	 * Verify the presence of Existing password window continue button
	 * 
	 * @return
	 */
	public boolean verify_existing_pwd_confirm_continue_button() {
		return elementVisibility(existing_pwd_confirm_button);
	}

	/**
	 * Get the text of Password Confirmation message
	 * 
	 * @return
	 */
	public String get_existing_pwd_confirm_msg() {
		return getText(existing_pwd_confirm_msg, 90);
	}

	/**
	 * To verify the ErrorMessage header on registration page
	 */
	public boolean verify_ErrorMessageHeader(String Headertext) {
		button(submit, 5);
		waitForElement(errorMessageContent, 10);
		return getText(errorMessageHeader, 5).equals(Headertext);
	}

	/**
	 * To verify the ErrorMessage content on registration page
	 */
	public boolean verify_ErrorMessageContent(String contenttext) {
		button(submit, 5);
		waitForElement(errorMessageContent, 10);
		return getText(errorMessageContent, 5).contains(contenttext);
	}

	/**
	 * UnCheck the First Optin checkbox from Registration page
	 */
	public void uncheck_first_optin_checkbox() {
		button(first_optin_checkbox, 15);
	}

	/**
	 * UnCheck the Second Optin checkbox from Registration page
	 */
	public void uncheck_second_optin_checkbox() {
		button(second_optin_checkbox, 15);
	}

	/**
	 * To verify that email is prefilled.
	 */
	public String getEmailAndConfirmEmailsValues() {
		if (getAttribute(email, "value").equals(getAttribute(confirmEmail, "value"))) {
			log.info("Email and Confirm Email fileds are returning same value");
			return getText(email, 10);
		} else {
			log.info("Email and Confirm Email fileds are not returning same value");
			return null;
		}

	}

	/**
	 * To verify the accounts register screen
	 */
	public boolean verify_AccountsRegisterScreen() {
		return elementPresent(title) && elementPresent(submit);
	}

	/**
	 * To verify that email and password are not present on Accounts register
	 * screen. Can used Mini reg user validations.
	 */
	public boolean verifyEmailPasswordNotPresent() {
		return elementPresent(email) == false && elementPresent(password) == false;
	}

	/**
	 * To register a new user with password and this method will close the bronze
	 * level up celebration too. Based on the argument it will update the Date of
	 * Birth value
	 */
	public String register_FullUser(String... modify_dob) throws Exception {
		String user_email = "PCHAuto" + randomString(5, 6) + Date() + "@pchmail.com";
		selectByVisibleText(title, xmlReader(ENVIRONMENT, "Title"), 5);
		textbox(firstName, "enter", randomString(6, 7), 5);
		textbox(lastName, "enter", xmlReader(ENVIRONMENT, "LastName"), 5);
		textbox(street, "enter", xmlReader(ENVIRONMENT, "Street"), 5);
		textbox(city, "enter", xmlReader(ENVIRONMENT, "City"), 5);
		selectByVisibleText(state, xmlReader(ENVIRONMENT, "State"), 5);
		textbox(zip, "enter", xmlReader(ENVIRONMENT, "Zip"), 5);
		if (modify_dob.length > 0) {
			selectByVisibleText(dob, modify_dob[0], 5);
			selectByVisibleText(mob, modify_dob[1], 5);
			selectByVisibleText(yob, modify_dob[2], 5);
		} else {
			selectByVisibleText(mob, xmlReader(ENVIRONMENT, "MonthinDOB"), 5);
			selectByVisibleText(dob, xmlReader(ENVIRONMENT, "DayinDOB"), 5);
			selectByVisibleText(yob, xmlReader(ENVIRONMENT, "YearinDOB"), 5);
		}
		textbox(email, "enter", user_email, 5);
		textbox(confirmEmail, "enter", user_email, 5);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		textbox(confirmPassword, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		button(submit, 10);
		sleepFor(5);
		lb_instance.close_bronze_level_up_lb();
		log.info("Newly Registered Email is :: " + user_email);
		System.out.println("Newly Registered Email is :: " + user_email);
		return user_email;
	}

	/**
	 * To register a new user without the Optin suggestion check box and this method
	 * will close the bronze level up celebration too.
	 */
	public String register_full_user_without_optin() throws Exception {
		String user_email = "PCHAuto" + randomString(5, 6) + Date() + "@pchmail.com";
		selectByVisibleText(title, xmlReader(ENVIRONMENT, "Title"), 5);
		textbox(firstName, "enter", randomString(6, 7), 5);
		textbox(lastName, "enter", xmlReader(ENVIRONMENT, "LastName"), 5);
		textbox(street, "enter", xmlReader(ENVIRONMENT, "Street"), 5);
		textbox(city, "enter", xmlReader(ENVIRONMENT, "City"), 5);
		selectByVisibleText(state, xmlReader(ENVIRONMENT, "State"), 5);
		textbox(zip, "enter", xmlReader(ENVIRONMENT, "Zip"), 5);
		selectByVisibleText(mob, xmlReader(ENVIRONMENT, "MonthinDOB"), 5);
		selectByVisibleText(dob, xmlReader(ENVIRONMENT, "DayinDOB"), 5);
		selectByVisibleText(yob, xmlReader(ENVIRONMENT, "YearinDOB"), 5);
		textbox(email, "enter", user_email, 5);
		textbox(confirmEmail, "enter", user_email, 5);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		textbox(confirmPassword, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		uncheck_first_optin_checkbox();
		uncheck_second_optin_checkbox();
		button(submit, 5);
		HomePage.getInstance().verify_Home();
		lb_instance.close_bronze_level_up_lb();
		log.info("Newly Registered Email is :: " + user_email);
		return user_email;
	}

	/**
	 * To register a new user without the Optin suggestion check box and this method
	 * will not close the bronze level up celebration too.
	 */
	public String register_full_user_without_optin_bronze_levelup() throws Exception {
		String user_email = "PCHAuto" + randomString(5, 6) + Date() + "@pchmail.com";
		selectByVisibleText(title, xmlReader(ENVIRONMENT, "Title"), 5);
		textbox(firstName, "enter", randomString(6, 7), 5);
		textbox(lastName, "enter", xmlReader(ENVIRONMENT, "LastName"), 5);
		textbox(street, "enter", xmlReader(ENVIRONMENT, "Street"), 5);
		textbox(city, "enter", xmlReader(ENVIRONMENT, "City"), 5);
		selectByVisibleText(state, xmlReader(ENVIRONMENT, "State"), 5);
		textbox(zip, "enter", xmlReader(ENVIRONMENT, "Zip"), 5);
		selectByVisibleText(mob, xmlReader(ENVIRONMENT, "MonthinDOB"), 5);
		selectByVisibleText(dob, xmlReader(ENVIRONMENT, "DayinDOB"), 5);
		selectByVisibleText(yob, xmlReader(ENVIRONMENT, "YearinDOB"), 5);
		textbox(email, "enter", user_email, 5);
		textbox(confirmEmail, "enter", user_email, 5);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		textbox(confirmPassword, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		uncheck_first_optin_checkbox();
		uncheck_second_optin_checkbox();
		button(submit, 5);
		HomePage.getInstance().verify_Home();
		log.info("Newly Registered Email is :: " + user_email);
		return user_email;
	}

	/**
	 * To complete registration for social user
	 */
	public void complete_RegistrationForSocialUser() throws Exception {

		selectByVisibleText(title, xmlReader(ENVIRONMENT, "Title"), 5);
		textbox(firstName, "enter", randomString(6, 7), 5);
		textbox(lastName, "enter", xmlReader(ENVIRONMENT, "LastName"), 5);
		textbox(street, "enter", xmlReader(ENVIRONMENT, "Street"), 5);
		textbox(city, "enter", xmlReader(ENVIRONMENT, "City"), 5);
		selectByVisibleText(state, xmlReader(ENVIRONMENT, "State"), 5);
		textbox(zip, "enter", xmlReader(ENVIRONMENT, "Zip"), 5);
		selectByVisibleText(mob, xmlReader(ENVIRONMENT, "MonthinDOB"), 5);
		selectByVisibleText(dob, xmlReader(ENVIRONMENT, "DayinDOB"), 5);
		selectByVisibleText(yob, xmlReader(ENVIRONMENT, "YearinDOB"), 5);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		textbox(confirmPassword, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		button(submit, 5);
		log.info("Social user Registration is completed");
		lb_instance.close_bronze_level_up_lb();
	}

	/**
	 * To complete registration for Mini Reg user
	 */
	public void complete_RegistrationForMiniRegUser() throws Exception {

		selectByVisibleText(title, xmlReader(ENVIRONMENT, "Title"), 5);
		textbox(firstName, "enter", randomString(6, 7), 5);
		textbox(lastName, "enter", xmlReader(ENVIRONMENT, "LastName"), 5);
		textbox(street, "enter", xmlReader(ENVIRONMENT, "Street"), 5);
		textbox(city, "enter", xmlReader(ENVIRONMENT, "City"), 5);
		selectByVisibleText(state, xmlReader(ENVIRONMENT, "State"), 5);
		textbox(zip, "enter", xmlReader(ENVIRONMENT, "Zip"), 5);
		selectByVisibleText(mob, xmlReader(ENVIRONMENT, "MonthinDOB"), 5);
		selectByVisibleText(dob, xmlReader(ENVIRONMENT, "DayinDOB"), 5);
		selectByVisibleText(yob, xmlReader(ENVIRONMENT, "YearinDOB"), 5);
		button(submit, 5);
		log.info("Mini Reg user Registration is completed");
		lb_instance.close_bronze_level_up_lb();
	}

	public void register_the_already_existing_user(String user_email) throws Exception {
		selectByVisibleText(title, xmlReader(ENVIRONMENT, "Title"), 5);
		textbox(firstName, "enter", randomString(6, 7), 5);
		textbox(lastName, "enter", xmlReader(ENVIRONMENT, "LastName"), 5);
		textbox(street, "enter", xmlReader(ENVIRONMENT, "Street"), 5);
		textbox(city, "enter", xmlReader(ENVIRONMENT, "City"), 5);
		selectByVisibleText(state, xmlReader(ENVIRONMENT, "State"), 5);
		textbox(zip, "enter", xmlReader(ENVIRONMENT, "Zip"), 5);
		selectByVisibleText(mob, xmlReader(ENVIRONMENT, "MonthinDOB"), 5);
		selectByVisibleText(dob, xmlReader(ENVIRONMENT, "DayinDOB"), 5);
		selectByVisibleText(yob, xmlReader(ENVIRONMENT, "YearinDOB"), 5);
		textbox(email, "enter", user_email, 5);
		textbox(confirmEmail, "enter", user_email, 5);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		textbox(confirmPassword, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 5);
		button(submit, 5);
	}

	public void completer_RegistrationSilveruser() throws Exception {
		textbox(password_silveruser, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 10);
		textbox(cnfmpassword_silveruser, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 10);
		button(submit_silveruser, 10);
	}

}
