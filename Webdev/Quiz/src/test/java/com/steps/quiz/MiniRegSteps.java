package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.LightboxPage;
import com.pages.quiz.RegistrationPage;
import com.pages.quiz.SignInPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class MiniRegSteps extends ScenarioSteps

{
	private static final long serialVersionUID = 1L;
	RegistrationPage reg;
	HeaderAndUninavPage uninav;
	SignInPage signIn;
	LightboxPage lbPage;

	@Step
	public void miniRegToFullReg() {
		uninav.clickCompleteRegistration();
		signIn.login(RegistrationPage.userPassword);
		reg.completeMiniRegUser();
	}

	@Step
	public boolean verifyCompleteregLb() {
		return lbPage.verifyCompleteRegLB();
	}

	@Step
	public void CompleteRegLbtofullReg() {
		lbPage.clickContinueBtnCompleteRegLb();
		signIn.login(RegistrationPage.userPassword);
		reg.completeMiniRegUser();
	}
}