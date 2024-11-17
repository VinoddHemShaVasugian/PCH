package com.pch.automation.steps.sw;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SignInPage;

import net.thucydides.core.steps.ScenarioSteps;

public class MiniRegSteps extends ScenarioSteps

{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RegistrationPage reg;
	HeaderAndUninavPage uninav;
	SignInPage signIn;
	LightboxPage lbPage;

	public void miniRegToFullReg() {
		uninav.clickCompleteRegistration();
		signIn.login(RegistrationPage.userPassword);
		reg.completeMiniRegUser();
	}

	public boolean verifyCompleteregLb() {
		return lbPage.verifyOptinCompleteRegLBforUsertypes();
	}

	public void CompleteRegLbtofullReg() {
		lbPage.clickContinueBtnCompleteRegLb();
		signIn.login(RegistrationPage.userPassword);
		reg.completeMiniRegUser();
	}

}
