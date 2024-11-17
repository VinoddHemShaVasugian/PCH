package com.pch.automation.stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.jbehave.core.annotations.Then;
import org.junit.Assert;

import com.pch.automation.steps.ContestEntrySteps;
import com.pch.automation.steps.jm.FpAdminSteps;
import com.pchengineering.billmecontestentries.databasehelper.UserEntries;

import net.thucydides.core.annotations.Steps;

public class ContestEnryScenarioSteps {

	@Steps
	ContestEntrySteps contestEntrySteps;

	/**
	 * Verify the contest entry based on Contest Key as articleName parameter
	 * 
	 * @param articleName
	 */
	@Then("Verify contest entry '$articleName','$labelName' for organic user")
	public void verifyContestEntriesForOrganicUser(String airtcleName, String labelName) {
		List<UserEntries> userEntry = contestEntrySteps.verifyContestEntryForOrganicUser();
		String expectedContestEntry = FpAdminSteps.getArticleDetails().get(airtcleName+labelName).trim();
		boolean ck = false;
		for (UserEntries ue : userEntry) {
			if (ue.getContestKey().trim().equalsIgnoreCase(expectedContestEntry)) {
				ck = true;
				break;
			}
		}
		if (!ck)
			Assert.assertTrue("Given contest entry is not awarded to the user", false);
	}
	
	/**
	 * Verify the contest entry based on Contest Key as articleName parameter
	 * 
	 * @param articleName
	 */
	@Then("Verify contest entry '$articleName' for organic user")
	public void verifyContestEntryForOrganicUser(String airtcleName) {
		String expectedContestEntry = "";
		List<UserEntries> userEntry = contestEntrySteps.verifyContestEntryForOrganicUser();
		if (FpAdminSteps.getArticleDetails().get(airtcleName) != null) {
			expectedContestEntry = FpAdminSteps.getArticleDetails().get(airtcleName).trim();
		} else {
			expectedContestEntry = airtcleName.trim();
		}
		boolean ck = false;
		for (UserEntries ue : userEntry) {
			if (ue.getContestKey().trim().equalsIgnoreCase(expectedContestEntry)) {
				ck = true;
				break;
			}
		}
		if (!ck)
			Assert.assertTrue("Given contest entry is not awarded to the user", false);
	}

	/**
	 * Verify the contest entry based on Contest Key as articleName parameter
	 * 
	 * @param articleName
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify contest entry '$articleName','$labelName'")
	public void verifyContestEntriesForRFUsers(String airtcleName, String labelName) throws SQLException, IOException {
		boolean ck = false;
		List<UserEntries> userEntry = contestEntrySteps.verifyContestEntryForUserCreatedFromRF();
		String expectedContestEntry = FpAdminSteps.getArticleDetails().get(airtcleName+labelName).trim();
		for (UserEntries ue : userEntry) {
			if (ue.getContestKey().trim().equalsIgnoreCase(expectedContestEntry)) {
				ck = true;
				break;
			}
		}
		if (!ck)
			Assert.assertTrue("Given contest entry is not awarded to the user", false);
	}

	@Then("Verify the failed contest entry record details with status as '$status'")
	public void verifyFiledContestEntries(String status) throws IOException, SQLException {
		assertEquals("Status is mismatched for the expected failed contest entry record", Integer.parseInt(status),
				contestEntrySteps.getFailedContestEntryStatus(status));
	}

	@Then("Verify the absence of the failed contest entry record")
	public void verifyAbsenceOfContestEntryRecord() throws IOException, SQLException {
		assertEquals("Failed contest entry records are still present in table after run the cron helper url", 0,
				contestEntrySteps.getContestEntryRecord());
	}
}