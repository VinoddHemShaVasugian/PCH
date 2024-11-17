package com.pch.automation.steps.fp;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.fp.HomePage;

public class SocialRegSteps {
	RegistrationPage reg;
	HeaderAndUninavPage uninav;
	LightboxPage lbPage;
	HomePage homePage;

	public void fullReg() {
		uninav.clickCompleteRegistration();
		reg.compelteSocialUser();
	}

	public boolean verifyCompleteregLb() {
		return lbPage.verifyOptinCompleteRegLBforUsertypes();
	}

	public void CompleteRegLbtofullReg() {
		lbPage.clickContinueBtnCompleteRegLb();
		reg.compelteSocialUser();
	}
}