package com.pch.survey.stepdefinitions.accounts;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;

import com.pch.survey.pages.accounts.AccountsPage;
import com.pch.survey.pages.accounts.CreatePasswordPage;
import com.pch.survey.pages.accounts.MpoRegistrationPage;
import com.pch.survey.pages.surveytab.SurveyMainPage;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.Constant;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * @author mmittal
 *
 */
public class AccountsStepDefinitions {

	private AccountsPage accountsPage = new AccountsPage(WebdriverBuilder.getDriver());
	public static int activityCount = 0;
	private MpoRegistrationPage mpoRegistrationPage = new MpoRegistrationPage(WebdriverBuilder.getDriver());

	@When("I click on My Account")
	public void clickMyAccount() {
		if (SurveyMainPage.user.equalsIgnoreCase("mini reg")) {
			accountsPage.clickCompleteRegLink();
			mpoRegistrationPage.completeMiniRegUser();
			accountsPage.navigateToMyAccountPage();
		} else {
			accountsPage.navigateToMyAccountPage();
		}
		Assert.assertTrue("My account page is not displayed.", accountsPage.verifyMyAccountPage());

	}

	@And("I verify non influencer token amount and description for category {string}")
	public void verifynonInfluencerTokenAmountAndDescription(String category) {
		category = category.substring(0, 1).toUpperCase() + category.substring(1);
		Assert.assertEquals("Description did not match", "Profile Question Answered For " + category,
				accountsPage.scrollGetDesc(category).getText());
		Assert.assertEquals("Token amount did not match", Constant.nonInfluencerTokens,
				accountsPage.scrollGetAmt(category).getText().substring(1));

	}

	@And("I verify influencer token amount and description for category {string}")
	public void verifyInfluencerTokenAmountAndDescription(String category) {
		category = category.substring(0, 1).toUpperCase() + category.substring(1);
		Assert.assertEquals("Description did not match", "Profile Question Answered For " + category,
				accountsPage.scrollGetDesc(category).getText());
		Assert.assertEquals("Token amount did not match", Constant.influencerTokens,
				accountsPage.scrollGetAmt(category).getText().substring(1));

	}

	@And("I verify influencer pro token amount and description for category {string}")
	public void verifyInfluencerProTokenAmountAndDescription(String category) {
		category = category.substring(0, 1).toUpperCase() + category.substring(1);
		Assert.assertEquals("Description did not match", "Profile Question Answered For " + category,
				accountsPage.scrollGetDesc(category).getText());
		Assert.assertEquals("Token amount did not match", Constant.influencerProTokens,
				accountsPage.scrollGetAmt(category).getText().substring(1));

	}

	@And("I verify influencer pro plus token amount and description for category {string}")
	public void verifyInfluencerProPlusTokenAmountAndDescription(String category) {
		category = category.substring(0, 1).toUpperCase() + category.substring(1);
		Assert.assertEquals("Description did not match", "Profile Question Answered For " + category,
				accountsPage.scrollGetDesc(category).getText());
		Assert.assertEquals("Token amount did not match", Constant.influencerProPlusTokens,
				accountsPage.scrollGetAmt(category).getText().substring(1));

	}

	@Then("I verify the awarded token of {string} with description {string} for recent activity")
	public void verifySurveyActivityTokenAmountAndDescription(String token, String description) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(token))
			token = ConfigurationReader.getAppConfigProperty().get(token);
		if (ConfigurationReader.getAppConfigProperty().containsKey(description))
			description = ConfigurationReader.getAppConfigProperty().get(description);
		assertEquals("Tokens details are failed to display on Token History for " + activityCount + " times",
				activityCount, accountsPage.verifyTokenTransactionsDetails(description, token, activityCount));
	}

	@Then("I verify the awarded token of {string} with description {string} for survey activity {string}")
	public void verifySurveyActivityTokenAmountAndDescription(String token, String description, String count) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(token))
			token = ConfigurationReader.getAppConfigProperty().get(token);
		if (ConfigurationReader.getAppConfigProperty().containsKey(description))
			description = ConfigurationReader.getAppConfigProperty().get(description);
		assertEquals("Tokens details are failed to display on Token History for " + Integer.parseInt(count) + " times",
				Integer.parseInt(count),
				accountsPage.verifyTokenTransactionsDetails(description, token, Integer.parseInt(count)));
	}

}
