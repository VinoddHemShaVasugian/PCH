package com.stepdefinition.quiz;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Then;

import com.steps.quiz.GOSSteps;

import net.thucydides.core.annotations.Steps;

public class LightboxScenarioSteps {

	@Steps
	GOSSteps gosSteps;

	@Then("Verify the presence of the guest user registration light box")
	public void verifyGuestUserLB() {
		gosSteps.clickPlayNextQuizLink();
		assertTrue("Guest user light box is failed to display", gosSteps.verifyGuestUserLB());
	}

	@Then("Verify the presence of Abandoning Token light box")
	public void verifyAbandoningTokenLB() {
		assertTrue("Abandoning Token light box is failed to display", gosSteps.verifyAbandoningTokenLB());
	}
}