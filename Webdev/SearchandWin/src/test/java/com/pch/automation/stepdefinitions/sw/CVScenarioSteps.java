package com.pch.automation.stepdefinitions.sw;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.sw.CVSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Consecutive Visits Scenario Steps
 * 
 * @author mperumal
 *
 */
public class CVScenarioSteps {

	@Steps
	CVSteps cvSteps;

	@Then("Verify the consecutive visit bar for the Day '$day'")
	public void verifyCVBar(int dayCount) {
		assertEquals("Failed to make the user consecutive visit as Day one", dayCount, cvSteps.getCVDayCount());
	}

	@Then("Verify the token message and token amount on Token History page based on admin property '$AlternativeDescription', '$Tokens','$position'")
	public void verifyCVMessage(String descProperty, String tokenProperty, String position) {
		assertEquals("Consecutive visit message failed to display on Token History tab for " + position + " time(s)",
				Integer.parseInt(position), cvSteps.verifyCVMsgFromTokenHistory(descProperty, tokenProperty, position));
	}

	@Then("Login to the Couchbase admin")
	public void loginCB() {
		cvSteps.loginCB();
	}

	@Then("Search for the user on couchbase")
	public void searchDoc() throws IOException {
		cvSteps.searchDoc();
	}

	// To do modify the search date on couch base doc.
	@Then("Modify the lastsearchdate to '$day' day past")
	public void loginmodifyLastSearchDateCB(String day) throws IOException {
		cvSteps.modifyLastSearchDate(day);
	}

	@Then("Save the couchbase document")
	public void saveDoc() {
		cvSteps.saveDoc();
	}

	@Then("Update search count to '$SearchCount'")
	public void updateSearchCount(String SearchCount) throws IOException, SQLException {
		cvSteps.updateSearchCount(SearchCount);
	}
}
