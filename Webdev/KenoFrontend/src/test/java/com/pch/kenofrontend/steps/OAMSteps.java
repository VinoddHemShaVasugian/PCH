package com.pch.kenofrontend.steps;

import com.pch.kenofrontend.pages.OAMPage;
import com.pch.kenofrontend.pages.RegistrationPage;
import com.pch.kenofrontend.pages.SignInPage;
import com.pch.kenofrontend.utilities.WaitHelper;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

public class OAMSteps{
		
	OAMPage oampg;
	SignInPage signInPage;
	
	
	@Step
	public void loginOAM() 
	{
		oampg.loginOAM();
	}

	@Step
	public void clickSearchMenu()
	{
		oampg.clickSearchMenu();
	}
	
	@Step
	public void clickLegacyContestEntryTab()
	{
		oampg.clickLegacyContestEntryTab();
	}
	
	@Step
	public void clicksOnSubscriptionsEventTab()
	{
		oampg.clickSubscriptionTab();
	}
	
	@Step
	public void enterUserWithoutPwdEmail() throws InterruptedException
	{
		oampg.enterUserWithoutPwdEmail();
	}
	
	@Step
	public void enterUserWithPwdEmail() throws InterruptedException
	{
		oampg.enterUserWithPwdEmail(RegistrationPage.uni_email);
	}
	
	@Step
	public void verifyContestEntryRecord() throws InterruptedException
	{
		oampg.verifyContestKeyInContestEntryRecord();
	}
	
	@Step
	public void verifyKenoOptinRecord() throws InterruptedException
	{
		oampg.verifyKenoOptinRecord();
	}
	
	@Step
	public void verifyOptinsPreReg()
	{
		oampg.loginOAM();
		oampg.clickSearchMenu();
		oampg.clickSubscriptionTab();		
		try {
			oampg.enterUserWithPwdEmail(RegistrationPage.email);
			oampg.verifyKenoOptinRecord();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
