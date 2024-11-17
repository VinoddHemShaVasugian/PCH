package com.pch.automation.steps.fp;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;

import net.thucydides.core.annotations.Step;

public class SilverRegSteps {
	RegistrationPage reg;
	HeaderAndUninavPage uninav;
	LightboxPage lbPage;

	@Step
	public void fullReg() {
		uninav.clickCompleteRegistration();
		reg.compelteSilverUser();
	}
	
	@Step
	public void fullRegFromCompleteRegLightbox() {
		lbPage.clickContinueBtnCompleteRegLb();
		reg.compelteSilverUser();
	}
}
