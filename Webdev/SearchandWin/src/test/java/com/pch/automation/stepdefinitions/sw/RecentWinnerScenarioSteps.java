/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation.stepdefinitions.sw;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.steps.*;
import com.pch.automation.steps.sw.RecentWinnerSteps;

import net.thucydides.core.annotations.Steps;


// TODO: Auto-generated Javadoc
/**
 * The Class RecentWinnerScenarioSteps.
 */
public class RecentWinnerScenarioSteps {

	/** The navigation steps. */
	@Steps
	NavigationSteps navigationSteps;
	
	/** The recent winner steps. */
	@Steps
	RecentWinnerSteps recentWinnerSteps;
	
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
		recentWinnerSteps.clickRecentWinner();
	}

	/**
	 * Then verify the winners list.
	 */
	@Then("Verify the Winners List")
	public void thenVerifyTheWinnersList() {
		recentWinnerSteps.verifyingRecentWinnerNames();
	}

}
