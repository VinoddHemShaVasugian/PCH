package com.pch.frontpage.steps;

import org.jbehave.core.model.ExamplesTable;

import com.pch.frontpage.pageObjects.AccountsSignInPage;
import com.pch.frontpage.pageObjects.JoomlaConfigPage;

import net.thucydides.core.annotations.Step;

public class AwardTokensSteps {
	
	AccountsSignInPage accountsSignInPage;
	JoomlaConfigPage joomlaConfigPage;
	
	
	@Step
	public void enterCredentials(ExamplesTable credentials)
	{
		accountsSignInPage.login(credentials);
		
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
