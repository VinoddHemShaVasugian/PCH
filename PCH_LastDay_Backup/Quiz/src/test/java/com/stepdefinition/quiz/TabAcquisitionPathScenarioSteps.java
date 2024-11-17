package com.stepdefinition.quiz;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.steps.quiz.TabAcquisitionPathSteps;

import java.io.IOException;
import java.net.URISyntaxException;

public class TabAcquisitionPathScenarioSteps {
	@Steps
	TabAcquisitionPathSteps acquisitionPathSteps;

	@When("land on quizzes Acquisition path")
	public void LandQuizzesSiteHavingPersistantBanner() throws URISyntaxException, InterruptedException, IOException {
		acquisitionPathSteps.QuizzesSiteWithAcq();
	}

	@Then("verify Persistent Banner")
	public void verifyPersistantBanner() throws URISyntaxException, InterruptedException {
		acquisitionPathSteps.IspersistentBannerDisplay();
	}

	@Then("verify user complete $path and verify entries and tokens")
	public void thenVerifyUserPathAndLandingScreen(String path) throws InterruptedException, IOException {
		acquisitionPathSteps.verifyUserPathAndLandingScreen(path);
	}

	@Then("verify clicking on Claim Now On quizPage leads to proper Registration Page")
	public void verifyClickingOnClaimNowOnQuizPageUserLandsOnRegistrationPage() throws InterruptedException, IOException {
		acquisitionPathSteps.verifyClickingOnClaimNowOnQuizPageUserLandsOnRegistrationPage();
	}

	@Then("verify Claim Now appears on QuizPage after exactly halfwaypath complete")
	public void verifyClaimNowAppearsOnQuizPage() {
		acquisitionPathSteps.verifyClaimNowAppearsOnQuizPage();
		Assert.assertTrue("Claim Now Link not appears on QuizPage",acquisitionPathSteps.verifyClaimNowAppearsOnQuizPage());
	}

	@Then("verify Claim Now appears on GOS")
	public void thenVerifyClaimNowAppearsOnGOS() {
		acquisitionPathSteps.verifyClaimNowAppearsOnGOS();
	}

	@Then("verify clicking on Claim Now leads to proper Registration Page")
	public void thenVerifyClickingOnClaimNowUserLandsOnRegistrationPage() throws InterruptedException {
		acquisitionPathSteps.verifyClickingOnClaimNowUserLandsOnRegistrationPage();
	}

	@Then("verify proper registration page loaded")
	public void thenVerifyProperRegistrationPageLoaded() throws InterruptedException {
		acquisitionPathSteps.verifyProperRegistrationPageLoaded();
	}

	@Then("verify registration done properly")
	public void thenRegistrationDone() throws InterruptedException {
		acquisitionPathSteps.verifyIfRegistrationDone();
	}

	@Then("verify if spectrum page appears then land on quizzes site")
	public void afterRegistrationSpectrumPageLandinOnQuizzes() throws InterruptedException {
		acquisitionPathSteps.verifyAfterRegistrationSpectrumPageAppears();
	}


	@Then("verify Tokens and Verbiage after $path Registration")
	public void thenVerifyTokensAndVerbiageAfterCompletingRegistration(String path) throws InterruptedException, IOException {
		acquisitionPathSteps.verifyTokensAndVerbiageAfterCompletingRegistration(path);
	}

	@Then("verify ContestEntries after $path Registration")
	public void thenVerifyEntriesAfterCompletingRegistration(String path) throws InterruptedException {
		acquisitionPathSteps.verifyEntriesAfterCompletingRegistration(path);
	}

}
