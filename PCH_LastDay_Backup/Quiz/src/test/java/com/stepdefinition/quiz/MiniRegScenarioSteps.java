package com.stepdefinition.quiz;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.steps.quiz.GOSSteps;
import com.steps.quiz.MiniRegSteps;

import net.thucydides.core.annotations.Steps;

public class MiniRegScenarioSteps {

	@Steps
	MiniRegSteps mini;
	@Steps
	GOSSteps gosSteps;

	@When("Do complete registration to become full reg user from Mini Reg user")
	@Then("Do complete registration to become full reg user from Mini Reg user")
	public void fullRegsuccessfull() {
		mini.miniRegToFullReg();
	}

	@Then("Verify the presence of the complete reg light box")
	public void verifyPresenceOfCompleteregLb() {
		gosSteps.clickPlayNextQuizLink();
		assertTrue("Complete registration light box is failed to display", mini.verifyCompleteregLb());
	}

	@Then("Do complete reg from complete reg Lightbox")
	public void doCompleteRegFromcomregLB() {
		mini.CompleteRegLbtofullReg();
	}
}