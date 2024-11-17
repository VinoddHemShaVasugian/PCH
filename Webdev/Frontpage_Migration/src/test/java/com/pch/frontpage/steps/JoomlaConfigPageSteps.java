package com.pch.frontpage.steps;


import com.pch.frontpage.pageObjects.AccountsSignInPage;
import com.pch.frontpage.pageObjects.JoomlaConfigPage;
import net.thucydides.core.annotations.Step;


public class JoomlaConfigPageSteps {
	
//	AccountsSignInPage accountsSignInPage;
	JoomlaConfigPage joomlaConfigPage;
	
	@Step
	public void login(String user_name, String password) {
		joomlaConfigPage.OpenFpJoomlaAdmin();
		joomlaConfigPage.log_in(user_name, password);
	}

}
