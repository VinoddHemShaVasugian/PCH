package com.pch.survey.stepdefinitions.profilebuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;

import com.pch.survey.pages.accounts.CreatePasswordPage;
import com.pch.survey.pages.profiles.ProfileBuilderPage;
import com.pch.survey.pages.profiles.ProfileBuilderSectionPage;
import com.pch.survey.stepdefinitions.CommonStepDefinitions;
import com.pch.survey.utilities.database.RedisClient;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
 

 


public class ProfileBuilderStepDefinitions   {

	private ProfileBuilderSectionPage profileSectionPage = new ProfileBuilderSectionPage(WebdriverBuilder.getDriver());
	private ProfileBuilderPage  profilePage = new ProfileBuilderPage(WebdriverBuilder.getDriver());
	private CreatePasswordPage  passwordPage = new CreatePasswordPage(WebdriverBuilder.getDriver());
	
//	private ProfileBuilderSectionPage profileSectionPage = new ProfileBuilderSectionPage();
//	private ProfileBuilderPage  profilePage = new ProfileBuilderPage();
//	private CreatePasswordPage  passwordPage = new CreatePasswordPage();

	
	@When("I select a profile category {string}")
	public void clickProfileCategoryArrow(String category) {
		profilePage.selectProfileCategory(category);
	}
	
	
	@Given("I agree to the terms of the Data Collection Consent")
	public void agreeToTerms() {
		profilePage.agreeDataColection();
	}
	
	
	@Then("I verify profile builder status message is {string} and % complete is {string}")
	public void verifyStatusMessage(String status,String pctComplete) {
		assertTrue("Verify profile status ",profilePage.getStatusMessage().contains(status));
		assertTrue("Verify % complete ",profilePage.getStatusMessage().contains(pctComplete));
	}
	
	
	
	
	@When("I answer all profile builder questions")
	public void answerAllCategoryQuestions() {
		profileSectionPage.answerAllProfileBuilderQuestions();
	}


	@When("I answer {int} profile builder questions")
	public void answerProfileQuestions(int count) {
		profileSectionPage.answerAllProfileBuilderQuestions(count);
	}
	
	
	
	@When("I answer question {string} with {string}")
	public void clickProfilesection(String question, String answer) {
			profileSectionPage.selectAndAnswerQuestion(question, answer);
	}
	

	@When("I click the save button")
	public void clickSaveButton() {
			profileSectionPage.clickSaveButton();
	}
	
	@Then("I click Exit button")
	public void clickExitButtonn() {
		profilePage.clickExitButton();
	}
	@Then("I click Exit button on question page")
	public void clickExitButtonOnQuestionSection() {
		profileSectionPage.clickExitButton();
	}
	
	
	
	
	@Then("I verify user name on create password page is {string}")
	public void verifyName(String name) {
		assertEquals(name, passwordPage.getUserName());
	}

	@Then("I verify the number of questions answered is recorded in Redis DB")
	public void verifyQuestionCountInDb() {

		String key = "s:pb:"+ new CommonStepDefinitions().getGmt()+":q";
		System.out.println("Redis key = " +key);
		Assert.assertEquals("Answered profile questions in DB ",profileSectionPage.getNumberOfQuestionsAnswered(),RedisClient.getResultAsMap(key).size());
	
	}
	
	@Then("I verify updated title is {string}")
	public void verifyProfileBuilderTitle(String txt) {
		Assert.assertEquals(txt, profilePage.getProfileBuilderTitle());
	}
 
	@Then("I verify % complete progress bar under the top bar")
	public void verifyProgressBar() {
		profilePage.PercentageCompletionProgressBar();
	}
	
	@Then("I verify the text copy next to % complete is {string}")
	public void verifyProfileBuilderTextCopy(String txt) {
		Assert.assertEquals(txt, profilePage.getTextNextToProgressBar());
	}
	
	@Then("I verify privacy policy link in the text copy")
	public void verifyPrivacyPolicyInTextCopy() {
		profilePage.PrivacyPolicyInText();
	}
	
	@Then("I verify exit button in the bottom of the page")
	public void verifyExitButtonInProfileBuilder() {
		profilePage.ExitButtonInProfileBuilder();
	}
	
}
