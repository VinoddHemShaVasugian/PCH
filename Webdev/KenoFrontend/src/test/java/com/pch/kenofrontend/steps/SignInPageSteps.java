package com.pch.kenofrontend.steps;

import org.jbehave.core.model.ExamplesTable;
import com.pch.kenofrontend.pages.SignInPage;
import net.thucydides.core.annotations.Step;

public class SignInPageSteps {
	
	SignInPage signInPage;
	
	@Step
	public void enterCredentials(ExamplesTable credentials)
	{
		signInPage.login(credentials);
		
    }
	
	@Step
	public void loginAsMiniUser() 
	{
		signInPage.loginAsMiniUser();
	}
	
	@Step
	public void loginIfSignInPageAppearing()
	{
		signInPage.loginIfSignInPageAppearing();
	}
	
	

}
