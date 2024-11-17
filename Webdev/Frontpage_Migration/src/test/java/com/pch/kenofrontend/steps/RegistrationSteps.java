package com.pch.kenofrontend.steps;

import org.jbehave.core.model.ExamplesTable;

import com.pch.kenofrontend.pages.HomePage;
import com.pch.kenofrontend.pages.RegistrationPage;
import com.pch.kenofrontend.pages.SignInPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class RegistrationSteps {
	
	@Steps
	BrowserSteps browserSteps;
	HomePage homePage;
	SignInPage signInPage;
	RegistrationPage registrationPage;
	
	@Step
	public void submit_Registration(ExamplesTable userData){

		registrationPage.verifyIf_RegistrationPage_Loaded();

		String title = userData.getRow(0).get("title");
		String firstName = userData.getRow(0).get("firstName");
		String lastName = userData.getRow(0).get("lastName");
		String street = userData.getRow(0).get("street");
		String suite = userData.getRow(0).get("suite");
		String city = userData.getRow(0).get("city");
		String state = userData.getRow(0).get("state");
		String zip = userData.getRow(0).get("zip");
		String month = userData.getRow(0).get("month");
		String day = userData.getRow(0).get("day");
		String year = userData.getRow(0).get("year");
		String email = userData.getRow(0).get("email");
		String confirmEmail = userData.getRow(0).get("confirmEmail");
		String password = userData.getRow(0).get("password");
		String confirmPassword = userData.getRow(0).get("confirmPassword");
		boolean keepSignedIn = Boolean.parseBoolean(userData.getRow(0).get("keepSignedIn"));
		boolean lottoOptin = Boolean.parseBoolean(userData.getRow(0).get("lottoOptin"));
		boolean pchOptin = Boolean.parseBoolean(userData.getRow(0).get("pchOptin"));

		registrationPage.properRegistration(title, firstName, lastName
				, street, suite, city, state, zip
				, month, day, year, email, confirmEmail
				, password, confirmPassword
				, keepSignedIn, lottoOptin, pchOptin);
	}

	@Step
	public void submit_Registration(){
		registrationPage.verifyIf_RegistrationPage_Loaded();
		registrationPage.magicRegistration();
	}
	
	@Step
	public void verifyUserInRegistrationPage()
	{
		registrationPage.verifyUserInRegistrationPage();
	}
	
	@Step
	public void verifySignInLink()
	{
		registrationPage.signinLinkPresent();
	}
	
	@Step
	public void verifyRegPageForMiniRegUser()
	{
		registrationPage.verifyRegPageForMiniRegUser();
	}
	
	@Step
	public void fillRegPageForMiniReg()
	{
		registrationPage.fill_data_for_MiniReg_User();		
	}
	
	@Step
	public void kenoSiteWithSocialUser()
	{
		registrationPage.kenoSiteWithSocialUser();
	}
	
	@Step
	public void verifyRegPageForSocialUser()
	{
		registrationPage.verifyRegPageForSocialUser();
	}
	
	@Step
	public void fillRegPageForSocialUser()
	{
		registrationPage.fill_data_for_FacebookwithEmail_Reg_User();
	}
	
	@Step
	public void kenoSiteWithMiniUser()
	{
		browserSteps.OpenKenoHomePage();
		homePage.clickOn_SignIn_Btn();
		signInPage.loginAsMiniUser();
	}
}
