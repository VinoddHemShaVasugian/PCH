package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.LightboxPage;
import com.pages.quiz.RegistrationPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class SilverRegSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;
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
		lbPage.clickContinueBtnNoPwdUserLb();
		reg.compelteSilverUser();
	}
	
	@Step
	public boolean verifyNoPwdUserCompleteregLb() {
		return lbPage.verifyNoPwdUserCompleteRegLB();
	}
}
