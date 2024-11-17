package com.pch.search.pages.web;


import java.text.DecimalFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

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
	
	public SelectList selectTitle() {		
			return driver.findSelectList(By.xpath("//select[contains(@class,'required title')]"));		
	}
	
	public HtmlElement firstName() {
		return driver.findElement(By.xpath("//input[contains(@class,'first-name')]"));	
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
	
	public HtmlElement optin1CheckBox(){
		return driver.findElement(By.id("optin1"));
	}
	
	public HtmlElement optin2CheckBox(){
		return driver.findElement(By.id("optin2"));
	}
	
	public HtmlElement submit(){
	//	if(device.equalsIgnoreCase("mobile")){
	//		return driver.findElement(By.xpath(".//*[@class='submit-container clearfix']/button"));
	//	}else{
			return waitForElementToBeVisible(By.xpath("//button[contains(@class,'submit-button')]"));
	//	}
	}
	
	public void disableOptin1(){
		if(optin1CheckBox().isSelected()){
			optin1CheckBox().click();
			CustomLogger.log("optin1 check box is Disabled");
		}else{
			CustomLogger.log("Optin1 Check box is already Disabled");
		}
	}
	
	public void disableOptin2(){
		if(optin2CheckBox().isSelected()){
			optin2CheckBox().click();
			CustomLogger.log("optin2 check box is Disabled");
		}else{
			CustomLogger.log("Optin2 Check box is already Disabled");
		}
	}
	
	public boolean isFieldDisabled(String fieldName){
		fieldName=fieldName+":";
		HtmlElement element = driver.findElement(By.xpath(String.format("//label[text()='%s']/following-sibling::*",fieldName)),30);
		if(element.getAttribute("readonly").equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	}
	public void justClickSubmitBtn(){
		CustomLogger.log("Submitting user details form");
		String errorElementXPath ;
		if(device.equalsIgnoreCase("mobile")){
			errorElementXPath = ".//*[@class='inner-shell email']";
		}else{
			errorElementXPath = "//div[@class='sso-outer-shell']/descendant::*[@class='error']";
		}
		boolean isErrorPresentOnRegistrationPage = driver.getCountOfElementsWithXPath(errorElementXPath)!=0;
		HtmlElement errorElement = null;
		if(isErrorPresentOnRegistrationPage){
			errorElement=driver.findElement(By.xpath(errorElementXPath));
		}

		submit().scrollDownAndClick();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clickSubmitButton() {
		CustomLogger.log("Submitting user details form");
		String errorElementXPath ;
		if(device.equalsIgnoreCase("mobile")){
			errorElementXPath = ".//*[@class='inner-shell email']";
		}else{
			errorElementXPath = "//div[@class='sso-outer-shell']/descendant::*[@class='error']";
		}

//		boolean isErrorPresentOnRegistrationPage = driver.getCountOfElementsWithXPath(errorElementXPath)!=0;
//		HtmlElement errorElement = null;
//		if(isErrorPresentOnRegistrationPage){
//			CustomLogger.log("test12");
//			errorElement=driver.findElement(By.xpath(errorElementXPath));
//		}

		submit().scrollDownAndClick();
		driver.waitForBrowserToLoadCompletely();
//		try {
//			Thread.sleep(3000);
//			CustomLogger.log("test02");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		driver.waitForBrowserToLoadCompletely();
//		if(isErrorPresentOnRegistrationPage){
//			CustomLogger.log("test03");
//			errorElement.waitTillNotPresent();
//		}
//		if(!(driver.getTitle().contains("GuidedSearch"))){
//			try{
//				CustomLogger.log("test04");
//				HtmlElement pleaseCorrect = driver.findElement(By.xpath(errorElementXPath),3);
//				CustomLogger.log("test05");
//				CustomLogger.log(String.format("Error in '%s' field",pleaseCorrect.getText()));
//			}catch(TimeoutException toe){
//				String submit = ".//*[@class='submit-container clearfix']/button";
//				CustomLogger.log("test06");
//				if(driver.getCountOfElementsWithXPath(submit)!=0){
//					CustomLogger.log("test07");
//					submit().waitTillNotPresent(10);
//					CustomLogger.log("Submit button not present anymore");
//					driver.waitForBrowserToLoadCompletely();
//					CustomLogger.log("test08");
//				}
//				CustomLogger.log("test107");
//				if(!device.equalsIgnoreCase("mobile")){
//					//submit().waitTillNotPresent(10);
//					if(driver.getCountOfElementsWithXPath(submit)!=0){
//						CustomLogger.log("test09");
//						CustomLogger.log("Submit button not present anymore");
//						driver.waitForBrowserToLoadCompletely();
//						CustomLogger.log("tes1t10");
//					}
//				}
//				CustomLogger.log("test1078");
//			}
//		}

	}

	public String getEmailMismatchErrorText(){
		return driver.findElement(
				By.xpath("//*[contains(@class,'inner-shell')]")).getText();
	}

	public String getSuccesfulRegistrationMessage(){
		return driver
				.findElement(By.xpath("//*[@class='profile-name']/a"))
				.getText();
	}

	public void enterUserDetails(User user){
		CustomLogger.log("Entering user details in registration form");
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if(user.getDob_Day()!=null){
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if(user.getDob_Month()!=null){
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if(user.getDob_Year()!=null){
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}
		
		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());
		
	}
	
	public void enterUserDetailsOptOut(User user){
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if(user.getDob_Day()!=null){
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if(user.getDob_Month()!=null){
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if(user.getDob_Year()!=null){
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}
		
		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());
		
		driver.findElement(By.id("optin1")).click();
		driver.findElement(By.id("optin2")).click();

	}
	
	public void enterUserDetailsSnWOptIn(User user){
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if(user.getDob_Day()!=null){
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if(user.getDob_Month()!=null){
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if(user.getDob_Year()!=null){
			dobYearListBox().selectByVisibleText(user.getDob_Year());
		}
		
		email().sendKeys(user.getEmail());
		confirmEmail().sendKeys(user.getEmail());
		password().sendKeys(user.getPassword());
		confirmPassword().sendKeys(user.getPassword());
		
		driver.findElement(By.id("optin2")).click();

	}
	
	public void enterUserDetailsPchComOptIn(User user){
		selectTitle().selectByVisibleText(user.getTitle());
		firstName().sendKeys(user.getFirstname());
		lastName().sendKeys(user.getLastname());
		streetAddress().sendKeys(user.getStreet());
		city().sendKeys(user.getCity());
		stateListBox().selectByVisibleText(user.getState());
		zip().sendKeys(user.getZip());

		DecimalFormat formatter = new DecimalFormat("00");

		if(user.getDob_Day()!=null){
			dobDayListBox().selectByVisibleText(formatter.format(Integer.parseInt(user.getDob_Day())));
		}

		if(user.getDob_Month()!=null){
			dobMonthListBox().selectByVisibleText(user.getDob_Month());
		}

		if(user.getDob_Year()!=null){
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

	public void enterFBUserDetails(User user){
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


	public String errorMessage() {

		String err = driver.findElement(
				By.xpath("//*[contains(@class,'inner-shell')]")).getText();
		return err;		
	}

	public SignInLightBox signInLightBox(){
		return new SignInLightBox(driver);
	}


}
