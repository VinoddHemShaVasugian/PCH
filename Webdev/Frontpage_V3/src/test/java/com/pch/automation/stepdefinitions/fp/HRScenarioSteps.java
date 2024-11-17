package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.fp.HRSteps;
import com.pch.automation.steps.jm.FpAdminSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains HR ligthbox Scenario Steps
 * 
 * @author mperumal
 *
 */
public class HRScenarioSteps {

	@Steps
	HRSteps hrsteps;

	/**
	 * Search for multiple times based on the configured value on admin
	 */
	@Then("Do a search with a random keyword for to admin configured property value '$duplicate_search_max_rate'")
	public void searchMultipleTimes(String duplicateSearchRate) {
		hrsteps.searchForMultipleTimes(duplicateSearchRate);
	}

	/**
	 * Search for a given keyword
	 */
	@Given("Do a search on Homepage with a random keyword")
	public void searchOnHomepage() {
		hrsteps.searchOnHomePage();
	}

	/**
	 * Search for a given keyword
	 */
	@Then("Do a search on SERPpage with a random keyword")
	public void searchOnSerp() {
		hrsteps.searchOnSerpPage();
	}

	/**
	 * Search for a given keyword
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	@Given("Update the search count on backend to admin configured property '$propertyName'")
	public void updateSearchCount(String propertyName) throws IOException, SQLException {
		hrsteps.updateCountDBProperty(FpAdminSteps.getArticleDetails().get(propertyName));
	}

	/**
	 * Verify the display of the HR Rapid light box
	 */
	@Then("Validate the display of the Rapid search light box")
	public void verifyRapidLighBox() {
		assertTrue("Rapid lightbox failed to display", hrsteps.verifyRapidHRLb());
		hrsteps.closeHRLb();
	}

	/**
	 * Verify the absence of the HR Rapid light box
	 */
	@Then("Validate the absence of the Rapid search light box")
	public void verifyAbsenceOfRapidLighBox() {
		assertFalse("Rapid lightbox should not get display", hrsteps.verifyRapidHRLb());
	}

	/**
	 * Verify the display of the Max Search HR light box
	 */
	@Then("Validate the display of the Max search light box")
	public void verifyMaxSearchLighBox() {
		assertTrue("Max Search lightbox failed to display", hrsteps.verifyMaxSearchHRLb());
		hrsteps.closeHRLb();
	}

	/**
	 * Verify the display of the Search Warning HR light box
	 */
	@Then("Validate the display of the Search Warning light box")
	public void verifySearchWarningLighBox() {
		assertTrue("Daily Limit Search warning lightbox failed to display", hrsteps.verifySearchWarningHRLb());
		hrsteps.closeHRLb();
	}

	/**
	 * Verify the display of the Search Disable HR light box
	 */
	@Then("Validate the display of the Search Disable light box")
	public void verifyDisableSearchLighBox() {
		assertTrue("Disable Search warning lightbox failed to display", hrsteps.verifyDisableSearchHRLb());
		hrsteps.closeFancyLb();
	}

	/**
	 * Verify the searchingenabled db property value as '1'
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the dbproperty searchingenabled with value as 0")
	public void verifySearchDisableDBProperty() throws IOException, SQLException {
		assertEquals("Searchingenabled property value failed to be as 0", 0,
				Integer.parseInt(hrsteps.verifySearchingDisbaledDBProperty()));
	}

	/**
	 * Verify the searchingenabled expires db property value as '0'
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the dbproperty searchingenabled with expires value as 0")
	public void verifySearchDisableExpireBProperty() throws IOException, SQLException {
		assertEquals("Searchingenabled property expire value failed to be as 0", 0,
				Integer.parseInt(hrsteps.verifySearchingDisbaledExpireBProperty()));
	}
}
