package com.stepdefinition.quiz;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.steps.quiz.GOSSteps;
import com.steps.quiz.SilverRegSteps;

import net.thucydides.core.annotations.Steps;

public class SilverRegScenarioSteps {

	@Steps
	SilverRegSteps silveruser;
	@Steps
	GOSSteps gosSteps;

	@When("Do complete registration to become fully registered user from Silver User")
	@Then("Do complete registration to become fully registered user from Silver User")
	public void silverTofullReg() {
		silveruser.fullReg();
	}

	@Then("Do complete registration to become fully registered user from light box")
	public void silverTofullRegFromSerp() {
		silveruser.fullRegFromCompleteRegLightbox();
	}
	
	@Then("Verify the presence of the complete reg light box for no password user")
	public void verifyPresenceOfNoPwdUserCompleteregLb() {
		gosSteps.clickPlayNextQuizLink();
		assertTrue("Complete reg light box is failed to display", silveruser.verifyNoPwdUserCompleteregLb());
	}
}