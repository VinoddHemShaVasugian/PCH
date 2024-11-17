package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.TokensSteps;
import com.pch.automation.steps.jm.FpAdminSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Extension tokens Scenario Steps
 * 
 * @author mperumal
 *
 */
public class ExtensionTokens {

	@Steps
	NavigationSteps navigation;
	@Steps
	TokensSteps tokens;
	HeaderAndUninavPage uninav;
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	/**
	 * Do a search on the home page
	 */
	@When("Redirect the user to extension download page '$pageUrl'")
	public void redirectExtensionDownloadPage(String pageUrl) {
		String currentUrl = navigation
				.navigateToFPApplication(configInstance.getEnvironmentProperty("AppUrl") + pageUrl);
		assertEquals("Chrome extension page is wrongly redirected",
				FpAdminSteps.getArticleDetails().get("extension_download_url"), currentUrl);
		navigation.navigateToFPApplication();
	}

	@When("Verify the db property '$dbProperty' as '$value'")
	@Then("Verify the db property '$dbProperty' as '$value'")
	public void verifyDbProp(String dbProperty, String value) throws IOException, SQLException {
		assertEquals("Invalid DB property for the Extension Tokens for Chrome extension page", value,
				tokens.verifyExtensionTokensDBProperty(dbProperty));
	}

	@Then("Redirect the user to '$searchExtension' page")
	public void redirectSearchExtensionPage(String searchExtensionPage) {
		navigation.navigateToFPApplication(configInstance.getEnvironmentProperty("AppUrl") + searchExtensionPage);
	}

	@Then("Verify the extension tokens and the db property '$searchandwin' as '$value'")
	public void verifyExtensionTokens(String dbProperty, String value) throws Exception {
		assertTrue("Extension token amount is not awarded to the user", tokens.getExtensionTokenAmount() >= Integer
				.parseInt(FpAdminSteps.getArticleDetails().get("tokens")));
		assertEquals("Invalid DB property for the Extension Tokens award", value,
				tokens.verifyExtensionTokensDBProperty(dbProperty));
	}

	@Then("Verify the extension tokens not get awarded")
	public void verifyExtensionTokens() throws Exception {
		assertFalse("Extension token amount is not awarded to the user", tokens.getExtensionTokenAmount() >= Integer
				.parseInt(FpAdminSteps.getArticleDetails().get("tokens")));
	}

	@Then("Complete the Mini reg user by click on Continue button from light box")
	public void completeMiniReg() throws Exception {
		tokens.completeMiniReg();
	}

	@Then("Complete the Social reg user by click on Continue button from light box")
	public void completeSocialReg() throws Exception {
		tokens.completeSocilaReg();
	}

	@Then("Verify the Complete Registration light box")
	public void verifyCompleteRegLb() throws Exception {
		assertTrue("Complete Registration light box failed to display", tokens.verifyCompleteRegLb());
	}

	@Then("Verify the Silver user Complete Registration light box")
	public void verifySilverCompleteRegLb() throws Exception {
		assertTrue("Complete Registration light box failed to display", tokens.verifySilverCompleteRegLb());
	}

	@Then("Complete the Silver reg user by enter the password")
	public void completeSilverReg() throws Exception {
		tokens.completeSilverReg();
	}

	@Then("Close the Complete Registration light box")
	public void closeCompleteRegLb() throws Exception {
		tokens.closeCompleteRegLb();
	}
}
