package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.steps.quiz.SocialRegSteps;

import net.thucydides.core.annotations.Steps;

public class SocialRegScenarioSteps {

	@Steps
	SocialRegSteps socialReg;

	@When("Complete registration to become full reg user from social reg user")
	@Then("Complete registration to become full reg user from social reg user")
	public void socialRegtoFullreg() {
		socialReg.fullReg();
	}

	@Then("Do complete registration from complete registration Lightbox to become full reg user")
	public void doCompleteRegFromcomregLB() {
		socialReg.CompleteRegLbtofullReg();
	}
}