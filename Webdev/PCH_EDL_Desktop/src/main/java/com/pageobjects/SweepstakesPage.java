package com.pageobjects;

public class SweepstakesPage {

	
	
	
	/**
	 * To register a new user with password and this method will close the
	 * bronze level up celebration too. Based on the argument it will update the
	 * Date of Birth value
	 */
	public String register_FullUser(String... modify_dob) throws Exception {
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
		HomePage.getInstance().verify_Home();
		lb_instance.close_bronze_level_up_lb();
		log.info("Newly Registered Email is :: " + user_email);
		return user_email;
	}
}
