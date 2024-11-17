package com.pch.survey.stepdefinitions.surveytab;

import org.junit.Assert;

import com.pch.survey.pages.surveytab.LightboxPage;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LightboxStepDefinitions {

	private LightboxPage lightboxPage = new LightboxPage(WebdriverBuilder.getDriver());

	@Then("I verify the onsite message popup {string}")
	public void thenVerifyOnsiteMsgPopup(String eventType) {
		Assert.assertTrue("Survey onsite message popup is not displayed.",
				lightboxPage.verifySurveyMsgPopupDisplayed());
		Assert.assertTrue("Survey onsite message popup is not displayed for " + eventType,
				lightboxPage.verifySubTitleFromOnsiteMsg(eventType));
	}

	@When("I click ANSWER SOME QUESTIONS button")
	public void whenClickSurveyButtonPopup() {
		lightboxPage.clickSurveyButton();
	}

	@Then("I close survey onsite message popup")
	public void whenCloseSurveyOnsitePopup() {
		lightboxPage.closeSurveyMsgPopup();
	}

	@Then("I verify an absence of survey onsite message popup")
	public void thenVerifyAbsenceOfOnsiteMsgPopup() {
		Assert.assertFalse("Survey onsite message popup is not closed.", lightboxPage.verifySurveyMsgPopupDisplayed());
	}
}