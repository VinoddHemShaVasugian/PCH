package com.pch.kenofrontend.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;


import com.pch.kenofrontend.steps.RegistrationSteps;
import com.pch.kenofrontend.steps.HomePageSteps;
import net.thucydides.core.annotations.Steps;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class RegistrationScenarioSteps {
	
	@Steps
	RegistrationSteps registrationSteps;
	
	
	@Steps
	HomePageSteps homePageSteps;
	
	@When("Enter the Required details in the registration page with optins unchecked")
	//@When("Enter the Required details in the registration page")
	public void whenEntertheRequireddetailsintheregistrationpage(){
		registrationSteps.submit_Registration();
	}
	
	
	@When("Verify application navigates to registration page")
	
	public void whenVerifyAppNavigatesToRegistrationPage()
	{
		registrationSteps.verifyUserInRegistrationPage();
	}
	
	
	@When("Registered a fresh user successfully")
	public void successfulRegistration()
	{
		homePageSteps.goto_RegistrationPage();
		registrationSteps.submit_Registration();
		homePageSteps.verifySuccessRegisration();
	}
    
    
	@Then ("It should show \"Sign In\" button on the top right of the registration Page")
    public void verifySignIn(){
		registrationSteps.verifySignInLink();	
		
    }
	
	@Given("Land on Keno Home Page as a fresh user with password using Reg Foundation")
	public void successfulRegistrationviaRS()
	{
		registrationSteps.createUserWithPasswordUsingRegFoundation();
		homePageSteps.verifySuccessRegisration();
	}
	
	@Then("optin lightbox should be displayed")
	public void optinLightboxDisplayed()
	{
		homePageSteps.optinLightboxDisplayed();
	}
	
	@Then("a click on \"X\" button should close the lightbox")
	@Given("click on 'X' button closes the optin lightbox")
	public void closeTheOptinLightbox()
	{
		homePageSteps.closeTheOptinLightbox();	
	}

	@Then ("user should see the Registration Form but without Email and Password fields on accounts site")
	public void verifyRegPageForMiniRegUser()
	{
		registrationSteps.verifyRegPageForMiniRegUser();
	}
	
	@Then ("user fills up the reg form for mini user with optins prechecked")
	public void fillRegPageForMiniReg()
	{
		registrationSteps.fillRegPageForMiniReg();		
	}
	
	@Given ("A social reg user is on keno site")
	public void kenoSiteWithSocialUser()
	{
		registrationSteps.kenoSiteWithSocialUser();
	}
	
	@Then ("user should see the Registration Form but with Email fields already filled on accounts site")
	public void verifyRegPageForSocialUser()
	{
		registrationSteps.verifyRegPageForSocialUser();
	}
	
	@Then ("user fills up the reg form for social user with optins prechecked")
	public void fillRegPageForSocialUser()
	{
		registrationSteps.fillRegPageForSocialUser();
	}
	
	@Given ("A minireg user is on keno site")
	public void kenoSiteWithMiniUser()
	{
		registrationSteps.kenoSiteWithMiniUser();
	}
}


