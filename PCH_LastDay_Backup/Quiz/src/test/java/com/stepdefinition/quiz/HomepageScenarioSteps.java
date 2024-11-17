package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.steps.quiz.CategorySteps;
import com.steps.quiz.HomepageSteps;
import com.steps.quiz.QuizSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Test scenarios for Homepage
 *
 * @author vsankar
 */

public class HomepageScenarioSteps {

	@Steps
	HomepageSteps homeSteps;
	@Steps
	QuizSteps quizSteps;
	@Steps
	CategorySteps categorySteps;

	@When("Navigate to Trending quiz page")
	@Then("Navigate to Trending quiz page")
	public void trendingQuiz() {
		homeSteps.navigateToTrendingQuiz();
		Assert.assertTrue("Trending quiz page is not rendered", quizSteps.verifyQuizPage());
	}
	
	@When("Navigate to '$Category' Category page")
	@Then("Navigate to '$Category' Category page")
	public void navigateToCategoryPages(String category) {
		homeSteps.navigateToCategoryPages(category);
		Assert.assertTrue("Category page is not displayed", categorySteps.verifyCategoryPages(category));
	}

	@When("Verify user landed on quiz site homepage")
	@Then("Verify user landed on quiz site homepage")
	public void verifySiteHomePage() {
		Assert.assertTrue("Quiz site home page is not displayed", homeSteps.verifySiteHomePage());
	}

	@Then("Verify the presence of signin and register button")
	public void verifyUninavForGuestUsers() {
		Assert.assertTrue("SignIn button is not displayed on uninav", homeSteps.verifySignInButton());
		Assert.assertTrue("Registration button is not displayed on uninav", homeSteps.verifyRegistrationButton());
	}

	@Then("Verify the signin page")
	public void verifySignInPage() {
		homeSteps.clickSignIn();
		Assert.assertTrue("Signin page not displayed", homeSteps.verifySignInPage());
	}

	@Then("Signin with existing user details")
	public void signIn() {
		homeSteps.signIn();
	}

	@When("Verify redeem tokens icon")
	public void verifyRedeemTokensIcon() {
		homeSteps.verifyRedeemToken();
	}

	@Then("Verify redeem tokens shelf")
	public void verifyRedeemTokens() {
		Assert.assertTrue("Redeem tokens shelf is not displayed", homeSteps.verifyRedeemTokenShelf());
	}

	@Then("Click levelup shelf and verify Playnow button")
	public void verifyLevelupPlaynowButtonFunctionality() {
		Assert.assertTrue("Levelup shelf is not displayed", homeSteps.openLevelUpShelf());
		Assert.assertTrue("Playnow button is not displayed", homeSteps.verifyPlaynowButton());
	}

	@Then("verify the infopages")
	public void verifyInfopageNavigation() {
		Assert.assertTrue("Quiz logo is not displayed", homeSteps.verifyLogo());
		Assert.assertTrue("Info pages links are not displayed", homeSteps.verifyInfoPages());
	}
}