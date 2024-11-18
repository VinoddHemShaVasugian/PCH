package com.pch.survey.stepdefinitions.surveytab;

/**
 * @author vsankar
 */
import org.junit.Assert;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.pages.surveytab.QOTDPage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.stepdefinitions.accounts.AccountsStepDefinitions;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QOTDStepDefinitions {

	private QOTDPage qotdPage = new QOTDPage(WebdriverBuilder.getDriver());
	private SurveyMainPage surveyPage = new SurveyMainPage(WebdriverBuilder.getDriver());
	private MpoRegistrationPage mpoRegistrationPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());
	private static String env = ConfigurationReader.getEnvironment().toLowerCase();

	private String questionsCount;
	private int Qapitoken;

	@Then("I complete QAPI flow and verify the status of progress bar")
	public void completeQAPIFlowVerifyTokenAndProgressBar() {
		String eligibleToken = ConfigurationReader.getQotdModuleToken().replace(",", "").trim();
		Qapitoken = Integer.parseInt(eligibleToken);
		for (int i = 2; i <= Integer.parseInt(questionsCount); i++) {
			try {
				qotdPage.answerQAPIQuestion();
				String progressBarStatus = qotdPage.getQapiProgressBarStatus();
				Assert.assertEquals("Failed to award eligible token for completed " + i + " QAPI question.",
						Qapitoken * i, Integer.parseInt(qotdPage.getQapiTokenAmt()));
				if (progressBarStatus.equalsIgnoreCase(questionsCount)) {
					break;
				}
				Assert.assertEquals("Progress bar status is not updated for completed " + i + " QAPI question.", i,
						Integer.parseInt(progressBarStatus));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Then("I verify the total tokens awarded on QAPI complete page")
	public void verifyTokenonQAPICompletePage() {
		Assert.assertEquals(
				"Failed to award/display eligible tokens for all the completed QAPI questions on QAPI complete page.",
				Qapitoken * Integer.parseInt(questionsCount), Integer.parseInt(qotdPage.getQapiCompletePageTokenAmt()));
	}

	@When("I click Continue on QAPI complete page")
	public void clickContinueOnQAPICompletePage() {
		qotdPage.clickQapiCompletePageContinueBtn();
	}

	@When("I fetch the total number of questions configured in QAPI module")
	public void getStepsCountMissionModule() {
		questionsCount = qotdPage.getQapiTotalQuestionsCount();
		AccountsStepDefinitions.activityCount = Integer.parseInt(questionsCount);
	}

	@Then("I verify {string} state of QOTD module")
	public void verifyQOTDModule(String state) {
		if (state.equalsIgnoreCase("Initial")) {
			Assert.assertTrue("Failed to display " + state + " state of QOTD module.",
					qotdPage.verifyQOTDInitialState());
			Assert.assertEquals("Failed to display " + state + " state of QOTD module.",
					ConfigurationReader.getQotdInitialStateDesc(), qotdPage.getQotdModuleDesc());
		} else if (state.equalsIgnoreCase("Incomplete")) {
			Assert.assertTrue("Failed to display " + state + " state of QOTD module.",
					qotdPage.verifyQOTDIncompleteState());
			Assert.assertEquals("Failed to display " + state + " state of QOTD module.",
					ConfigurationReader.getQotdIncompleteStateDesc(), qotdPage.getQotdModuleDesc());
		} else if (state.equalsIgnoreCase("Completed")) {
			Assert.assertTrue("Failed to display " + state + " state of QOTD module.",
					qotdPage.verifyQOTDCompleteState());
		}
	}

	@When("I complete QOTD module for {string} user")
	public void completeQOTDModule(String user) {
		String userType = user;
		if (userType.equalsIgnoreCase("silver")) {
			qotdPage.answerQotdQuestion();
		} else if (userType.equalsIgnoreCase("guest")) {
			if (WebdriverBuilder.getDriver().getCurrentUrl().contains("https://mpo." + env + ".pch.com/")) {
				mpoRegistrationPage.CreateFullRegUserForm();
			}
			qotdPage.answerQotdQuestion();
		} else {
			qotdPage.answerQotdQuestion();
		}

	}

	@When("I answer QOTD module")
	public void answerQOTDModule() {
		qotdPage.answerQotdQuestion();
	}

	@Then("I verify the disabled status of SAVE button before answering the question")
	public void verifySAVEButtonDisabledStatus() {
		Assert.assertFalse("Failed to disable SAVE button, beofre answering the question.",
				qotdPage.verifySaveButtonStatus());
	}

	@When("I click 'LetsGo' on QOTD module")
	public void navigateToQOTDModule() {
		qotdPage.clickLetsGoButton();
		surveyPage.closePhpdebugbar();
	}

	@When("I accept the SAVE confirmation popup")
	public void acceptSavePopup() {
		qotdPage.clickFeedbackPopupAcceptBtn();
	}

	@Then("I verify the UI elements on QOTD page")
	public void verifyUIElementsQOTDModule() {
		Assert.assertEquals("Failed to display QOTD page Disclaimer title.", ConfigurationReader.getDisclaimerTitle(),
				qotdPage.getQotdDisclaimerTitle());
		Assert.assertTrue("Failed to display Disclaimer description on QOTD page.",
				qotdPage.getQotdDisclaimerDesc().contains(ConfigurationReader.getDisclaimerDesc()));
		Assert.assertTrue("Failed to display First name on QOTD module.",
				qotdPage.getQotdSectionHeader().contains(Registrations.getFirstName()));
		Assert.assertTrue("Failed to display eligible token amount on QOTD module.",
				qotdPage.getQotdSectionHeader().contains(ConfigurationReader.getQotdModuleToken()));
		Assert.assertTrue("Failed to display Question Of The Day heading on QOTD module.",
				qotdPage.getQotdSectionTitle().contains("Question Of The Day"));
		Assert.assertTrue("Failed to display EXIT button on QOTD Module.", qotdPage.verifyExitButton());
		Assert.assertTrue("Failed to display SAVE button in disabled mode on QOTD Module.",
				qotdPage.verifySaveButton());
		Assert.assertTrue("Failed to display Official Rules link on QOTD Module uninav.",
				qotdPage.verifyQotdRulesLink());
		Assert.assertTrue("Failed to display Sweepstake Facts link on QOTD Module uninav.",
				qotdPage.verifyQotdSweepstakesLink());
		Assert.assertTrue("Failed to display Privacy Policy link on QOTD Module uninav.",
				qotdPage.verifyQotdPrivacyPolicyLink());
	}

	@Then("I verify the UI elements on SAVE confirmation popup")
	public void verifyUIElementsSAVEconfirmationPopup() {
		Assert.assertTrue("Failed to display SAVE confirmation popup.", qotdPage.verifyQotdFeedbackPopup());
		Assert.assertTrue("Failed to display First name on SAVE confirmation popup.",
				qotdPage.getFeedbackPopupTitle().contains(Registrations.getFirstName()));
		Assert.assertTrue("Failed to display eligible token amount on SAVE confirmation popup.",
				qotdPage.getFeedbackPopupSubTitle().contains(ConfigurationReader.getQotdModuleToken()));
		Assert.assertTrue("Failed to display NO button on SAVE confirmation popup.",
				qotdPage.verifyFeedbackPopupNoBtn());
		Assert.assertTrue("Failed to display YES button on SAVE confirmation popup.",
				qotdPage.verifyFeedbackPopupAcceptBtn());
	}

	@When("I click EXIT button")
	public void navigateToExitPopup() {
		qotdPage.clickExitButton();
	}

	@When("I accept the EXIT confirmation popup")
	public void acceptExitPopup() {
		qotdPage.clickExitPopupAcceptBtn();
	}

	@Then("I verify the UI elements on EXIT confirmation popup")
	public void verifyUIElementsEXITconfirmationPopup() {
		Assert.assertTrue("Failed to display EXIT confirmation popup.", qotdPage.verifyExitPopup());
		Assert.assertTrue("Failed to display description on EXIT confirmation popup.",
				qotdPage.getExitPopupDesc().trim().contains(ConfigurationReader.getQapiExitPopupDesc().trim()));
		Assert.assertTrue("Failed to display NO button on EXIT confirmation popup.", qotdPage.verifyExitPopupNoBtn());
		Assert.assertTrue("Failed to display YES button on EXIT confirmation popup.",
				qotdPage.verifyExitPopupAcceptBtn());
	}

	@Then("I verify the OfficialRules link redirection")
	public void verifyOfficialRulesPageRedirections() {
		WebdriverBuilder.getDriver().switchTo().window(qotdPage.getWindowHandle());
		qotdPage.clickQotdRulesLink();
		qotdPage.switchToLastOpenTab();
		Assert.assertTrue("Failed to redirect Official Rules page.",
				qotdPage.getCurrentURL().contains(ConfigurationReader.getQotdModuleOfficialRules()));
	}

	@Then("I verify the Sweepstake Facts link redirection")
	public void verifySweepstacksPageRedirections() {
		WebdriverBuilder.getDriver().switchTo().window(qotdPage.getWindowHandle());
		qotdPage.clickQotdSweepstakesLink();
		qotdPage.switchToLastOpenTab();
		Assert.assertTrue("Failed to redirect Sweepstake Facts page.",
				qotdPage.getCurrentURL().contains(ConfigurationReader.getQotdModuleSweepstakeFacts()));
	}

	@Then("I verify the Privacy Policy link redirection")
	public void verifyPrivacyPolicyPageRedirections() {
		WebdriverBuilder.getDriver().switchTo().window(qotdPage.getWindowHandle());
		qotdPage.clickQotdPrivacyPolicyLink();
		qotdPage.switchToLastOpenTab();
		Assert.assertTrue("Failed to redirect Privacy Policy page.",
				qotdPage.getCurrentURL().contains(ConfigurationReader.getQotdModulePrivacyPolicy()));
	}

	@Then("I verify the Privacy Policy link redirection on QAPI page")
	public void verifyPrivacyPolicyPageRedirectionOnQAPIPage() {
		WebdriverBuilder.getDriver().switchTo().window(qotdPage.getWindowHandle());
		qotdPage.clickQAPIPrivacyPolicy();
		qotdPage.switchToLastOpenTab();
		Assert.assertTrue("Failed to redirect Privacy Policy page.",
				qotdPage.getCurrentURL().contains(ConfigurationReader.getQotdModulePrivacyPolicy()));
		WebdriverBuilder.getDriver().switchTo().window(qotdPage.getWindowHandle());
	}

	@Then("I verify the QAPI page")
	public void verifyQAPIPage() {
		Assert.assertTrue("Failed to display QAPI page.", qotdPage.verifyQAPIPage());
		Assert.assertTrue("Failed to display QAPI page.",
				qotdPage.getCurrentURL().contains("question-set/qotd/offers-qotd"));
	}

	@Then("I verify the QOTD page")
	public void verifyQOTDPage() {
		Assert.assertTrue("Failed to display QAPI page.", qotdPage.verifyQOTDModule());
		Assert.assertTrue("Failed to display QAPI page.",
				qotdPage.getCurrentURL().contains("question-set/qotd/offers-qotd"));
	}

	@Then("I verify the UI elements on QAPI page")
	public void verifyUIElementsQAPIPage() {
		Assert.assertEquals("Failed to display the Disclaimer title on QAPI page.",
				ConfigurationReader.getDisclaimerTitle(), qotdPage.getQotdDisclaimerTitle());
		Assert.assertTrue("Failed to display the Disclaimer description on QAPI page.",
				qotdPage.getQotdDisclaimerDesc().contains(ConfigurationReader.getDisclaimerDesc()));
		Assert.assertTrue("Failed to display First name on QAPI page.",
				qotdPage.getQotdDisclaimerDesc().contains(Registrations.getFirstName()));
		Assert.assertTrue("Failed to display eligible token amount on QAPI page.",
				qotdPage.getQotdSectionHeader().contains(ConfigurationReader.getQotdModuleToken()));
		Assert.assertTrue("Failed to display ProgressBar on QAPI page.", qotdPage.verifyQapiProgressBar());
		Assert.assertTrue("Failed to display Token Module on QAPI page.", qotdPage.verifyQapiTokenModule());
		Assert.assertTrue("Failed to display EXIT button on QAPI page.", qotdPage.verifyExitButton());
		Assert.assertTrue("Failed to display SAVE button in disabled mode on QAPI page.", qotdPage.verifySaveButton());
		Assert.assertTrue("Failed to display Official Rules link on QAPI page.", qotdPage.verifyQotdRulesLink());
		Assert.assertTrue("Failed to display Sweepstake Facts link on QAPI page.",
				qotdPage.verifyQotdSweepstakesLink());
		Assert.assertTrue("Failed to display Privacy Policy link on QAPI page uninav.",
				qotdPage.verifyQotdPrivacyPolicyLink());
	}

	@Then("I verify the UI elements on SignUp confirmation popup")
	public void thenVerifyUIElementsSignupconfirmationPopup() {
		Assert.assertTrue("Failed to display SignUp confirmation popup.", qotdPage.verifyQotdGuestSuccessLb());
	}

	@When("I click continue on SignUp confirmation popup")
	public void whenClickOnSignupconfirmationPopup() {
		qotdPage.clickQotdGuestLbContinueBtn();
	}

	@Then("I verify the UI elements on Create password popup")
	public void thenVerifyUIElementsCreatePasswordPopup() {
		Assert.assertTrue("Failed to display create password popup.", qotdPage.verifyQotdCreatePasswordLb());
		Assert.assertTrue("Failed to display First name on create password popup.",
				qotdPage.getQotdCreatePwdLbTitle().contains(Registrations.getFirstName()));
		Assert.assertTrue("Failed to display description on create password popup.", qotdPage.getQotdCreatePwdLbDesc()
				.contains(ConfigurationReader.getAppConfigProperty().get("qotdCreatePwdLbDesc")));
		Assert.assertTrue("Failed to display eligible token amount on create password popup.", qotdPage
				.getQotdCreatePwdLbDesc().contains(ConfigurationReader.getAppConfigProperty().get("qotdModuleToken")));
		Assert.assertTrue("Failed to display suprise token amount for creating password.",
				qotdPage.getQotdCreatepwdLbExtraTokenAmt()
						.contains(ConfigurationReader.getAppConfigProperty().get("createPasswordTokens")));
		Assert.assertTrue("Failed to display no thanks link on create password popup.",
				qotdPage.verifyQotdCreatePwdLbNoThanksLink());
		Assert.assertTrue("Failed to display privacy policy link on create password popup.",
				qotdPage.verifyQotdCreatePwdLbPrivacyPolicyLink());
	}

	@When("I complete create password on QOTD flow")
	public void whenCompleteCreatePasswordPopup() {
		qotdPage.enterPasswordOnCreatePwdLbPasswordField();
		qotdPage.enterPasswordOnCreatePwdLbConfirmPasswordField();
		qotdPage.clickQotdCreatePwdLbSubmit();
	}

	@Then("I verify the UI elements on Create password confirmation popup")
	public void thenVerifyUIElementsCreatePasswordConfirmationPopup() {
		Assert.assertTrue("Failed to display Create password confirmation popup.",
				qotdPage.verifyQotdCreatePwdSuccessLb());
		Assert.assertTrue("Failed to display Yes button on Create password confirmation popup.",
				qotdPage.verifyQotdCreatePwdSuccessLbAcceptBtn());
		Assert.assertTrue("Failed to display No button on  Create password confirmation popup.",
				qotdPage.verifyQotdCreatePwdSuccessLbNoBtn());
		Assert.assertTrue("Failed to display First name on create password confirmation popup.",
				qotdPage.getQotdCreatePwdSuccessLbMsg().contains(Registrations.getFirstName()));
		Assert.assertTrue("Failed to display confirmation message on create password confirmation popup.",
				qotdPage.getQotdCreatePwdSuccessLbMsg()
						.contains(ConfigurationReader.getAppConfigProperty().get("qotdCreatePwdLbSuccessMsg")));
		Assert.assertTrue("Failed to display earned token amount on create password confirmation popup.",
				qotdPage.getQotdCreatePwdSuccessLbTokenAmt()
						.contains(ConfigurationReader.getAppConfigProperty().get("qotdModuleToken")));
	}

	@When("I accept the create password confirmation popup")
	public void whenAcceptCreatePasswordconfirmationPopup() {
		qotdPage.clickQotdCreatePwdSuccessLbAcceptBtn();
	}

}