package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Then;
import org.junit.Assert;

import com.steps.quiz.GOSSteps;

import net.thucydides.core.annotations.Steps;

public class GosScenarioSteps {

	@Steps
	GOSSteps gosSteps;

	@Then("Retrive legacy GOS details")
	public void getGosDetails() {
		gosSteps.getGosAttributes();
	}

	@Then("Navigate to next quiz from GOS")
	public void navigateToNextQuiz() {
		gosSteps.clickPlayNextQuizLink();
	}

	@Then("Verify legacy GOS details")
	public void verifyGosDetails() {
		Assert.assertTrue("Legacy GOS page is not displayed", gosSteps.verifyLegacyGosLayout());
		Assert.assertTrue("GOS next quiz button is not displayed", gosSteps.verifyNextQuizBtn());
		Assert.assertTrue("Token amount on GOS is not displayed", gosSteps.verifyTokenAmtGos());
		Assert.assertTrue("Great Job! message is not displayed on GOS", gosSteps.verifyCongratsMsg());
	}
}