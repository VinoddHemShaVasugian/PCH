package com.pch.offers.stepdefinitions;

import java.util.List;

import org.junit.Assert;

import com.pch.survey.centralservices.ContestEntries;
import com.pch.survey.utilities.ConfigurationReader;
import com.pchengineering.billmecontestentries.databasehelper.UserEntries;

import io.cucumber.java.en.Then;

public class ContestEntryStepDefinitions {

	private ContestEntries contestEnties = new ContestEntries();

	@Then("Verify contest entry {string}")
	public void verifyContestEntries(String expectedContestEntry) {
		if (ConfigurationReader.getAppConfigProperty().containsKey(expectedContestEntry))
			expectedContestEntry = ConfigurationReader.getAppConfigProperty().get(expectedContestEntry);
		boolean ck = false;
		List<UserEntries> userEntry = contestEnties.verifyContestEntry();
		for (UserEntries ue : userEntry) {
			if (ue.getContestKey().trim().equalsIgnoreCase(expectedContestEntry)) {
				ck = true;
				break;
			}
		}
		if (!ck)
			Assert.assertTrue("Given contest entry is not awarded to the user", false);
	}

}