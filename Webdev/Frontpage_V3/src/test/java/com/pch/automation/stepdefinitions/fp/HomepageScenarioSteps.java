package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertTrue;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.steps.HomepageSteps;

import net.thucydides.core.annotations.Steps;

public class HomepageScenarioSteps {

	@Steps
	HomepageSteps homepageSteps;

	@Then("Verify the presence of the Top stories section")
	public void verifyTopStoreisSection() {
		assertTrue("Failed to display the Top Stories section", homepageSteps.verifyTopStoriesSection());
	}

	@Then("Verify the presence of the Categories section")
	public void verifyCategoriessSection() {
		assertTrue("Failed to display the Categories section", homepageSteps.verifyCategoriessSection());
	}

	@When("Verify the presence of the Our Picks section")
	public void verifyOurpicksSection() {
		assertTrue("Failed to display the Our Picks section", homepageSteps.verifyOurpicksSection());
	}

	@Then("Verify the presence of the Trending Now section")
	public void verifyTrendingNowSection() {
		assertTrue("Failed to display the Trending Now section", homepageSteps.verifyTrendingNowSection());
	}

	@Then("Verify the presence of the Trending Videos section")
	public void verifyTrendingVideosSection() {
		assertTrue("Failed to display the Trending Videos section", homepageSteps.verifyTrendingVideosSection());
	}

	@Then("Verify the presence of '$category' category in Our Picks section")
	public void verifyCategoriesOnOurpicksSection(String category) {
		assertTrue("Failed to display the " + category + " category in Our Picks",
				homepageSteps.verifyCategoryOnOurpicks(category));
	}

	@Given("Navigate to Entertainment page")
	public void clickEntertainmentMenu() {
		homepageSteps.clickEntertainmentMenu();
	}

	@Given("Navigate to Weather page")
	public void clickWeatherMenu() {
		homepageSteps.clickWeatherMenu();
	}

	@Given("Navigate to Lottery page")
	public void clickLotteryMenu() {
		homepageSteps.clickLotteryMenu();
	}

	@Given("Navigate to News page")
	public void clickNewsMenu() {
		homepageSteps.clickNewsMenu();
	}

	@Then("Verify the infopages")
	public void verifyinfopageNavigation() {
		assertTrue("Info page are failed to load", homepageSteps.verifyInfoPages());
	}
}