package com.pch.search.pages.web;

import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.pch.search.pages.lightBox.SignInLightBox;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.SelectList;
import com.pch.search.utilities.User;

public class RegistrationPage extends Action {

	String device = Environment.getDevice();
	public By title = By.id("regform_title");
	public By first_name = By.id("regform_first");
	public By last_name = By.id("regform_last");
	public By street = By.id("regform_street");
	public By apt = By.id("regform_apt");
	public By city = By.id("regform_city");
	public By state = By.xpath("//select[@class='reg-input required state']");
	public By zipcode = By.id("regform_zip");
	public By month = By.xpath("//select[@class='reg-input month']");
	public By day = By.xpath("//select[@class='reg-input day']");
	public By year = By.xpath("//select[@class='reg-input year']");
	public By email = By.id("regform_email");
	public By eamil_confirm = By.id("regform_emailc");
	public By password = By.id("regform_password");
	public By password_confirm = By.id("regform_passwordc");

	public SelectList selectTitle() {
		return driver.findSelectList(title);
	}

	public HtmlElement firstName() {
		return driver.findElement(first_name);
	}

	public HtmlElement lastName() {
		return driver.findElement(By.xpath("//input[contains(@class,'last-name')]"));
	}

	public HtmlElement streetAddress() {
		return driver.findElement(By.xpath("//input[contains(@class,'required street')]"));
	}

	public HtmlElement apt() {
		return driver.findElement(By.xpath("//input[contains(@class,'reg-input apt')]"));
	}

	public HtmlElement city() {
		return driver.findElement(By.xpath("//input[contains(@class,'required city')]"));
	}

	public SelectList stateListBox() {

		return driver.findSelectList(By.xpath("//select[contains(@class,'required state')]"));

	}

	public HtmlElement zip() {
		return driver.findElement(By.xpath("//input[contains(@class,'required zip')]"));
	}

	public SelectList dobMonthListBox() {
		return driver.findSelectList(By.xpath("//select[contains(@class,'reg-input month')]"));
	}

	public SelectList dobDayListBox() {
		return driver.findSelectList(By.xpath("//select[contains(@class,'reg-input day')]"));
	}

	public SelectList dobYearListBox() {
		return driver.findSelectList(By.xpath("//select[contains(@class,'reg-input year')]"));
	}

	public HtmlElement email() {
		return driver.findElement(By.xpath("//input[contains(@class,'required email')]"));
	}

	public HtmlElement confirmEmail() {
		return driver.findElement(By.xpath("//input[contains(@class,'required confirm-email')]"));
	}

	public HtmlElement password() {
		return driver.findElement(By.xpath("//input[contains(@class,'required password')]"));
	}

	public HtmlElement confirmPassword() {
		return driver.findElement(By.xpath("//input[contains(@class,'required confirm-password')]"));
	}

	public HtmlElement optin1CheckBox() {
		return driver.findElement(By.id("optin1"));
	}

	public HtmlElement optin2CheckBox() {
		return driver.findElement(By.id("optin2"));
	}

	public HtmlElement submit() {
		// if(device.equalsIgnoreCase("mobile")){
		// return driver.findElement(By.xpath(".//*[@class='submit-container
		// clearfix']/button"));
		// }else{
		return waitForElementToBeVisible(By.xpath("//button[contains(@class,'submit-button')]"));
		// }
	}

	public void disableOptin1() {
		if (optin1CheckBox().isSelected()) {
			optin1CheckBox().click();
			CustomLogger.log("optin1 check box is Disabled");
		} else {
			CustomLogger.log("Optin1 Check box is already Disabled");
		}
	}

	public void disableOptin2() {
		if (optin2CheckBox().isSelected()) {
			optin2CheckBox().click();
			CustomLogger.log("optin2 check box is Disabled");
		} else {
			CustomLogger.log("Optin2 Check box is already Disabled");
		}
	}

	public boolean isFieldDisabled(String fieldName) {
		fieldName = fieldName + ":";
		HtmlElement element = driver.findElement(By.xpath("//*[@id='regform_email']"), 30);
		if (element.getAttribute("readonly").equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public HtmlElement mobile_reg_form_error_msg() {
		return driver.findElement(By.xpath("//div[@class='message show']"));
	}

	public HtmlElement Submit() {
		return driver.findElement(By.id("sub-btn"));
	}

	public void clickSubmitButton() {
		CustomLogger.log("Submitting user details form");
		// String errorElementXPath;
		//
		// errorElementXPath = "//div[@class='inner-shell email']";

		// boolean isErrorPresentOnRegistrationPage =
		// driver.getCountOfElementsWithXPath(errorElementXPath) != 0;
		// HtmlElement errorElement = null;
		// if (isErrorPresentOnRegistrationPage) {
		// errorElement = driver.findElement(By.xpath(errorElementXPath));
		// }

		submit().scrollDownAndClick();
		driver.waitForBrowserToLoadCompletely();
		// try {
		// Thread.sleep(6000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// driver.waitForBrowserToLoadCompletely();
		// if (isErrorPresentOnRegistrationPage) {
		// errorElement.waitTillNotPresent();
		// }
		// try {
		// HtmlElement pleaseCorrect =
		// driver.findElement(By.xpath(errorElementXPath), 3);
		// CustomLogger.log(String.format("Error in '%s' field",
		// pleaseCorrect.getText()));
		// } catch (TimeoutException toe) {
		// String submit = ".//*[@class='submit-container clearfix']/button";
		// if (driver.getCountOfElementsWithXPath(submit) != 0) {
		// submit().waitTillNotPresent(10);
		// CustomLogger.log("Submit button not present anymore");
		// driver.waitForBrowserToLoadCompletely();
		// }
		// if (!device.equalsIgnoreCase("mobile")) {
		// // submit().waitTillNotPresent(10);
		// if (driver.getCountOfElementsWithXPath(submit) != 0) {
		// CustomLogger.log("Submit button not present anymore");
		// driver.waitForBrowserToLoadCompletely();
		// }
		// }
		// }

	}

	public String getEmailMismatchErrorText() {
		return driver.findElement(By.xpath("//*[contains(@class,'inner-shell')]")).getText();
	}

	public String getSuccesfulRegistrationMessage() {
		return driver.findElement(By.xpath("//*[@class='profile-name']/a")).getText();
	}

	public void enterUserDetails(User user) {
		CustomLogger.log("Entering user details in registration form");
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if (user.getDob_Day() != null) {
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if (user.getDob_Month() != null) {
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if (user.getDob_Year() != null) {
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}

		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());

	}

	public void enterUserDetailsOptOut(User user) {
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if (user.getDob_Day() != null) {
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if (user.getDob_Month() != null) {
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if (user.getDob_Year() != null) {
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}

		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());

		driver.findElement(By.id("optin1")).click();
		driver.findElement(By.id("optin2")).click();

	}

	public void enterUserDetailsSnWOptIn(User user) {
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if (user.getDob_Day() != null) {
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if (user.getDob_Month() != null) {
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if (user.getDob_Year() != null) {
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}

		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());

		driver.findElement(By.id("optin2")).click();

	}

	public void enterUserDetailsPchComOptIn(User user) {
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if (user.getDob_Day() != null) {
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if (user.getDob_Month() != null) {
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if (user.getDob_Year() != null) {
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}

		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());

		driver.findElement(By.id("optin1")).click();

	}

	public void enterAddress(User address, String email, String password) {

		/*
		 * Calendar calendar = Calendar.getInstance(); int date =
		 * calendar.get(Calendar.DAY_OF_MONTH); int month =
		 * calendar.get(Calendar.MONTH); int year = 1980;
		 */

		selectTitle().selectByVisibleText(address.getTitle());
		firstName().sendKeys(Common.getRandomUserName("FN-"));
		lastName().sendKeys(Common.getRandomUserName("LN-"));
		streetAddress().sendKeys(address.getStreet());
		city().sendKeys(address.getCity());
		stateListBox().selectByVisibleText(address.getState());
		zip().sendKeys(address.getZip());
		email().sendKeys(email);
		confirmEmail().sendKeys(email);
		password().sendKeys(password);
		confirmPassword().sendKeys(password);

	}

	public void enterFBUserDetails(User user) {
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");
		dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));

		dobMonthListBox().selectByVisibleText(user.getDob_Month());
		dobYearListBox().selectByVisibleText(user.getDob_Year());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());
	}

	public void enterMiniregUserDetails(User user) {
		driver.waitForBrowserToLoadCompletely();
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());
	}

	public String signInErrorMessage() {

		String err = driver.findElement(By.xpath("//*[contains(@class,'inner-shell')]")).getText();
		return err;
	}

	public boolean regPageErrorMessage(By by) {
		// String by_for_error_field = by.toString() + "";
		WebElement ele = driver.getwrappedDriver().findElement(by)
				.findElement(By.xpath("//*[contains(@class,'sso-highlight')]"));

		// HtmlElement ele =
		// driver.findElement(by).findElement(By.xpath("//*[contains(@class,'sso-highlight')]"));
		return waitForElementToBeVisible(ele) != null ? true : false;
	}

	public SignInLightBox signInLightBox() {
		return new SignInLightBox(driver);
	}

}
