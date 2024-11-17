package com.pch.automation.steps.sw;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;

import net.thucydides.core.annotations.Step;

public class SocialRegSteps {
	RegistrationPage reg;
	HeaderAndUninavPage uninav;
	LightboxPage lbPage;

	@Step
	public void fullReg() {
		uninav.clickCompleteRegistration();
		reg.compelteSocialUser();
	}

	@Step
	public boolean verifyCompleteregLb() {
		return lbPage.verifyOptinCompleteRegLBforUsertypes();
	}

	@Step
	public void CompleteRegLbtofullReg() {
		lbPage.clickContinueBtnCompleteRegLb();
		reg.compelteSocialUser();
	}
}