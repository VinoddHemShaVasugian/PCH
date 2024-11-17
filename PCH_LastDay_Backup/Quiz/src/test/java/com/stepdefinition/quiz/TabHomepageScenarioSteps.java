package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.steps.quiz.TabHomepageSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Test scenarios for Tab Home page
 *
 * @author vsankar
 */

public class TabHomepageScenarioSteps {

	@Steps
	TabHomepageSteps homeSteps;

	@When("Verify user landed on quiz tab homepage")
	@Then("Verify user landed on quiz tab homepage")
	public void verifyTabHomePage() {
		Assert.assertTrue("Quiz tab home page is not displayed", homeSteps.verifyTabHomePage());
	}
}