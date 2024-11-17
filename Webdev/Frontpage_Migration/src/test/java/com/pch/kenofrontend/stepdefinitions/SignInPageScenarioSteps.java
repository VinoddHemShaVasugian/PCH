package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.steps.SignInPageSteps;

import net.thucydides.core.annotations.Steps;

public class SignInPageScenarioSteps {
	
	@Steps
	SignInPageSteps signInPageSteps;
	
	
	@When("Enter the valid credentials in SignIn page $credentials")
	public void whenEnterTheValidCredentialsInSignInPage(ExamplesTable credentials) {
		signInPageSteps.enterCredentials(credentials);
	}
	
	@When ("mini reg user logs in")
	public void loginAsMiniUser() 
	{
		signInPageSteps.loginAsMiniUser();
	}
	
	@Then ("user logs in if sign in page appears")
	public void loginIfSignInPageAppearing()
	{
		signInPageSteps.loginIfSignInPageAppearing();
	}
	
	
	

}
