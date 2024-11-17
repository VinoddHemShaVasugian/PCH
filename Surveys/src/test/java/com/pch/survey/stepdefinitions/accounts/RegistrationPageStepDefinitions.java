package com.pch.survey.stepdefinitions.accounts;

import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.utilities.RandomDataGenerator;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Given;

 


public class RegistrationPageStepDefinitions   {

 	private MpoRegistrationPage registrationPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());
	 
	
 	@Given("I register a new user")
	public void iResgisteraUser() {
  			String emailId = RandomDataGenerator.getRandomEmail();
 			String title = "Mr.";
 			String firstname = RandomDataGenerator.autoGenerateName();
 			String lastname = RandomDataGenerator.autoGenerateName();;
 			String streetaddress = "382 Channel Drive";
 			String zip = "11050";
 			String month = "February";
 			String day = "4";
 			String year = "1962";
 	  		registrationPage.selectTitle(title);
 			registrationPage.typeFirstName(firstname);
 			registrationPage.typeLastName(lastname);
 			registrationPage.typeStreetAddress(streetaddress);
 			registrationPage.typeZip(zip);
 			registrationPage.selectMonthOfBirth(month);
 			registrationPage.selectDayOfBirth(day);
 			registrationPage.selectYearOfBirth(year);
 			registrationPage.typeEmail(emailId);
 			registrationPage.typeConfirmEmail(emailId);
 			registrationPage.typePassword("pch123");
 			registrationPage.typeConfirmPassword("pch123");
 			registrationPage.clickSubmitButton();
 		}

	}
	
	
 
 
