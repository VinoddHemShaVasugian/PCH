/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.sample.steps.NavigationSteps;
import com.pch.sample.steps.SampleSteps;

import net.thucydides.core.annotations.Steps;


/**
 * The Class SampleScenarioSteps.
 */
public class SampleScenarioSteps {

	/** The navigation steps. */
	@Steps
	NavigationSteps navigationSteps;
	
	/** The sample steps. */
	@Steps
	SampleSteps sampleSteps;
	
	/**
	 * Given goto search and win site.
	 */
	@Given("Goto Search and Win Site")
	public void givenGotoSearchAndWinSite() {
		navigationSteps.navigateToApplication();
	}

	/**
	 * When click the recent winners.
	 */
	@When("Click the 'Recent Winners'")
	public void whenClickTheRecentWinners() {
		sampleSteps.clickRecentWinner();
	}

	/**
	 * Then verify the winners list.
	 */
	@Then("Verify the Winners List")
	public void thenVerifyTheWinnersList() {
		sampleSteps.verifyingRecentWinnerNames();
	}

}
