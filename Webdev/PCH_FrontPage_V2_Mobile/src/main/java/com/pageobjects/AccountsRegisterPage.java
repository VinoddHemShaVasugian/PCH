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
	private final By title = By.name("regform_title");
	private final By firstName = By.name("regform_first");
	private final By lastName = By.name("regform_last");
	private final By street = By.name("regform_street");
	private final By city = By.name("regform_city");
	private final By state = By.cssSelector("select.reg-input.required.state");
	private final By zip = By.name("regform_zip");
	private final By mob = By.cssSelector("select.reg-input.month");
	private final By dob = By.cssSelector("select.reg-input.day");
	private final By yob = By.cssSelector("select.reg-input.year");
	private final By email = By.name("regform_email");
	private final By confirmEmail = By.name("regform_emailc");
	private final By password = By.name("regform_password");
	private final By confirmPassword = By.name("regform_passwordc");
	private final By submit = By.id("sub-btn");
	private final By first_optin_checkbox = By.id("optin1");
	private final By second_optin_checkbox = By.id("optin2");
	private final By silver_user_pwd = By.name("password");
	private final By silver_user_confirm_pwd = By.name("confirm-password");
	private final By silver_user_submit_btn = By.cssSelector("button.create-now");
	private final By reg_form_error_message = By.cssSelector("div.message");

	private final By password_reset_email_screen_button = By.cssSelector("button.continue-btn");
	private final By password_reset_email_text1_locator = By.cssSelector("div#msg_area >p:nth-of-type(1)");
	private final By password_reset_email_text2_locator = By.cssSelector("div#msg_area >p:nth-of-type(2)");

	private final By password_continue_button = By.cssSelector("button.mandate-confirmed-btn");
	private final By continue_password_screen_email = By.id("EM");
	private final By password_continue_screen_text1_locator = By.xpath("//form/p[1]");
	private final By password_continue_screen_text2_locator = By.xpath("//form/p[2]");

	private final String password_reset_email_text1 = "A password reset link has been sent to your email address.";
	private final String password_reset_email_text2 = "After your password has been set, click below to sign in.";
	private final String password_continue_screen_text1 = "We already have your email on file.";
	private final String password_continue_screen_text2 = "But in order to continue, you must create a password with us.";

	// Check your email now for the Create Password link.
	// For your security, a password is required to modify any account
	// information.
	/**
	 * 
	 * @return the Error Message of the registration page
	 */
	public String verify_reg_form_error_message() {
		button(submit, 30);
		return getText(reg_form_error_message, 5);
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
	 * To verify that email is pre-filled.
	 */
	public String get_email_and_confirm_email_values() {
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
	public boolean verify_register_page() {
		return elementPresent(title) && elementPresent(submit);
	}

	/**
	 * To verify that email and password are not present on Accounts register
	 * screen. Can used Mini reg user validations.
	 */
	public boolean verify_email_password_field_absence_for_mini_reg_user() {
		return elementPresent(email) == false && elementPresent(password) == false;
	}

	/**
	 * To verify that email fields are not present on Accounts register screen.
	 * Can used Social reg user validations.
	 */
	public boolean verify_email_field_uneditable_mode_for_social_reg_user() {
		return getAttribute(email, "readonly").equals("true");
	}

	/**
	 * To register a new user with password and this method will close the
	 * bronze level up celebration too. Based on the argument it will update the
	 * Date of Birth value
	 */
	public String register_full_user_with_optin(String... modify_dob) throws Exception {
		String user_email = "PCH" + randomString(5, 6) + Date() + "@pchmail.com";
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
		lb_instance.close_bronze_level_up_lb();
		log.info("Newly Registered Email is :: " + user_email);
		return user_email;
	}

	/**
	 * To register a new user without the Optin suggestion check box and this
	 * method will close the bronze level up celebration too.
	 */
	public String register_full_user_without_optin() throws Exception {
		String user_email = "PCH" + randomString(5, 6) + Date() + "@pchmail.com";
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
		// lb_instance.close_bronze_level_up_lb();
		log.info("Newly Registered Email is :: " + user_email);
		return user_email;
	}

	/**
	 * To complete registration for Mini Reg user
	 */
	public void complete_registration_for_mini_reg_user() throws Exception {

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
	}

	/**
	 * To complete registration for Social user
	 */
	public void complete_registration_for_social_user() throws Exception {

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
	 * Complete the Registration for the Silver User
	 * 
	 * @throws Exception
	 */
	public void complete_registration_for_silver_user() throws Exception {
		textbox(silver_user_pwd, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 10);
		textbox(silver_user_confirm_pwd, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 10);
		button(silver_user_submit_btn, 10);
	}

	/**
	 * Register the created(via RF) Mini Reg user from the application
	 * registration form
	 * 
	 * @param user_email
	 * @throws Exception
	 */
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

	/**
	 * Verify the Silver user continue password screen
	 * 
	 * @return
	 */
	public void verify_silver_user_continue_password_screen(String email) {
		elementVisibility(password_continue_button, 30);
		assertEqualsIgnoreCase(getText(password_continue_screen_text1_locator, 30), password_continue_screen_text1);
		assertEqualsIgnoreCase(getText(password_continue_screen_text2_locator, 30), password_continue_screen_text2);
		assertEqualsIgnoreCase(getAttribute(continue_password_screen_email, "value"), email);
	}

	/**
	 * Verify the Silver user continue password screen
	 * 
	 * @return
	 */
	public void verify_silver_user_password_reset_email_screen() {
		elementVisibility(password_reset_email_screen_button, 30);
		assertEqualsIgnoreCase(getText(password_reset_email_text1_locator, 30), password_reset_email_text1);
		assertEqualsIgnoreCase(getText(password_reset_email_text2_locator, 30), password_reset_email_text2);
	}

	/**
	 * Click on Silver User Continue password button
	 */
	public void click_silver_user_continue_button() {
		button(password_continue_button, 30);
	}

	/**
	 * Click on Silver User Reset password email button
	 */
	public void click_silver_user_reset_password_email_button() {
		button(password_reset_email_screen_button, 30);
	}

}
