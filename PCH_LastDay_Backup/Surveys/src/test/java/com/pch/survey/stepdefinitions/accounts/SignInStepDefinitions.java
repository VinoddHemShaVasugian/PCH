package com.pch.survey.stepdefinitions.accounts;

import com.pch.survey.pages.accounts.SignInPage;
import com.pch.survey.stepdefinitions.CommonStepDefinitions;
import com.pch.survey.webdrivers.WebdriverBuilder;
import io.cucumber.java.en.Given;

public class SignInStepDefinitions   {
		
 	private SignInPage signPage = new SignInPage(WebdriverBuilder.getDriver());

    	@Given("The User Signs In with email {string} and password {string}")
    	public void givenEditTheStartDateAndEndDate(String email, String password ) {
      			if(email.equalsIgnoreCase("fromReg"))
    				email = new CommonStepDefinitions().getEmail();	 
    			if(password.equalsIgnoreCase("fromReg"))
    				password = new CommonStepDefinitions().getPassword();	 
    			signPage.typeEmail(email);
    			signPage.typePassword(password);
    			signPage.clickSignIn();
    		
    		}
   
    	
}
