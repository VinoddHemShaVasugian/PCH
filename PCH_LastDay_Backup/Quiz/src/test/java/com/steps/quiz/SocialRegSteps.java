package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.LightboxPage;
import com.pages.quiz.RegistrationPage;

public class SocialRegSteps {
	RegistrationPage reg;
	HeaderAndUninavPage uninav;
	LightboxPage lbPage;

	public void fullReg() {
		uninav.clickCompleteRegistration();
		reg.compelteSocialUser();
	}

	public boolean verifyCompleteregLb() {
		return lbPage.verifyCompleteRegLB();
	}

	public void CompleteRegLbtofullReg() {
		lbPage.clickContinueBtnCompleteRegLb();
		reg.compelteSocialUser();
	}
}