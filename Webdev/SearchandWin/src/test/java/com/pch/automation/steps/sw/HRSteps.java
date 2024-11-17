package com.pch.automation.steps.sw;

import java.io.IOException;
import java.sql.SQLException;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SerpPage;
import com.pch.automation.pages.sw.HomePage;
import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.utilities.RandomGenerator;
import com.pch.automation.utilities.WebServiceClient;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class HRSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	LightboxPage lbPage;
	HomePage hmPage;
	SerpPage serpPage;
	HeaderAndUninavPage uninavPage;
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();
	WebServiceClient webClient = WebServiceClient.getInstance();
	RegistrationPage regPage;

	/**
	 * Continuously do the search on the serp page
	 * 
	 * @return
	 */
	@Step
	public void searchForMultipleTimes(String duplicateSearchRate) {
		String keyTerm = RandomGenerator.randomAlphabetic(6);
		hmPage.search(keyTerm);
		serpPage.consecutiveSearches(Integer.parseInt(SearchAdminSteps.getArticleDetails().get(duplicateSearchRate)),
				keyTerm);
	}

	/**
	 * Search for a word
	 */
	@Step
	public void searchOnHomePage() {
		hmPage.search(RandomGenerator.randomAlphabetic(6));
	}

	/**
	 * Search for a word
	 */
	@Step
	public void searchOnSerpPage() {
		serpPage.search(RandomGenerator.randomAlphabetic(6));
	}

	/**
	 * Verify the Rapid HR light box
	 * 
	 * @returnF
	 */
	@Step
	public boolean verifyRapidHRLb() {
		return lbPage.verifyRapidHRLightbox();
	}

	/**
	 * Verify the Max Search HR light box
	 * 
	 * @returnF
	 */
	@Step
	public boolean verifyMaxSearchHRLb() {
		return lbPage.verifyMaxSearchHRLightbox();
	}

	/**
	 * Verify the Search warning HR light box
	 * 
	 * @returnF
	 */
	@Step
	public boolean verifySearchWarningHRLb() {
		return lbPage.verifyDailyLimitSearchHRLightbox();
	}

	/**
	 * Verify the Disable Search HR light box
	 * 
	 * @returnF
	 */
	@Step
	public boolean verifyDisableSearchHRLb() {
		return lbPage.verifyDisableSearchHRLightbox();
	}

	/**
	 * Close the HR light box
	 */
	@Step
	public void closeHRLb() {
		lbPage.closeHRLightbox();
	}

	/**
	 * Update the DB property for the Search count value
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Step
	public int updateCountDBProperty(String resetCountValue) throws IOException, SQLException {
		resetCountValue = String.valueOf(Integer.parseInt(resetCountValue) - 1);
		return dbHelper.updateDailySearchCount(regPage.getLastCreatedRFUserDetails().getEmail(), resetCountValue);
	}

	/**
	 * Return the DB Property of Search Disabled state
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Step
	public String verifySearchingDisbaledDBProperty() throws IOException, SQLException {
		String userId = webClient.getUserIdFromEmail(regPage.getLastCreatedRFUserDetails().getEmail());
		String query = "select value from sso_user_data where user = '" + userId
				+ "' and item like 'searchingenabled';";
		return dbHelper.executeQuery(query);
	}

	/**
	 * Return the Expire DB Property of Search Disabled state
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Step
	public String verifySearchingDisbaledExpireBProperty() throws IOException, SQLException {
		String userId = webClient.getUserIdFromEmail(regPage.getLastCreatedRFUserDetails().getEmail());
		String query = "select expires from sso_user_data where user = '" + userId
				+ "' and item like 'searchingenabled';";
		return dbHelper.executeQuery(query);
	}
}
