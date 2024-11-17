package com.steps.quiz;

import net.thucydides.core.annotations.Step;

import java.io.IOException;
import java.net.URISyntaxException;

import com.pages.quiz.RegistrationPage;
import com.pages.quiz.TabAcquisitionQuizPage;

public class TabAcquisitionPathSteps {
	TabAcquisitionQuizPage acquisitionQuizPage;
	RegistrationPage registrationPage;

	@Step
	public void QuizzesSiteWithAcq() throws URISyntaxException, InterruptedException, IOException {
		acquisitionQuizPage.gotoQuizzesAcqPath();
	}

	@Step
	public void IspersistentBannerDisplay() throws URISyntaxException, InterruptedException {
		acquisitionQuizPage.persistentBannerisDisplay();
	}

	@Step
	public void verifyUserPathAndLandingScreen(String path) throws InterruptedException, IOException {
		acquisitionQuizPage.quizPathCompletion(path);
	}

	@Step
	public void verifyClickingOnClaimNowOnQuizPageUserLandsOnRegistrationPage() throws InterruptedException, IOException {
		acquisitionQuizPage.landOnRegPageAfterHalfwayPathCompletion();
	}
	@Step
	public boolean verifyClaimNowAppearsOnQuizPage() {
		return acquisitionQuizPage.isClaimNowAppearsOnQuizPage();}

	@Step
	public void verifyClaimNowAppearsOnGOS() {
		acquisitionQuizPage.isClaimNowButtonDisplayOnGOS();
	}

	@Step
	public void verifyClickingOnClaimNowUserLandsOnRegistrationPage() throws InterruptedException {
		acquisitionQuizPage.landOnRegPageAfterPathCompletion();
	}

	@Step
	public void verifyProperRegistrationPageLoaded() throws InterruptedException {
		registrationPage.verifyIf_RegistrationPage_Loaded();
	}

	@Step
	public void verifyIfRegistrationDone() throws InterruptedException {
		acquisitionQuizPage.fullRegistration();
	}

	@Step
	public void verifyAfterRegistrationSpectrumPageAppears() throws InterruptedException {
		acquisitionQuizPage.isSpectrumPathDisplay();
	}

	@Step
	public void verifyTokensAndVerbiageAfterCompletingRegistration(String path) throws InterruptedException, IOException {
		acquisitionQuizPage.fullPathRegistrationTokensVerification(path);
	}

	@Step
	public void verifyEntriesAfterCompletingRegistration(String path) throws InterruptedException {
		acquisitionQuizPage.verifyContestEntriesInOAM(path);
	}

}
