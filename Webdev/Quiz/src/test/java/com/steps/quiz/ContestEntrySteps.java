package com.steps.quiz;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.pages.quiz.RegistrationPage;
import com.pch.quiz.database.DatabaseHelper;
import com.pch.quiz.utilities.AppConfigLoader;
import com.pch.quiz.utilities.WebServiceClient;
import com.pchengineering.billmecontestentries.databaseentries.ContestEntriesHelper;
import com.pchengineering.billmecontestentries.databasehelper.UserEntries;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

public class ContestEntrySteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Steps
	NavigationSteps navigationSteps;

	DatabaseHelper dbHelper = DatabaseHelper.getInstance();

	/**
	 * Verify contest entry
	 * 
	 * @return
	 */
	@Step
	public List<UserEntries> verifyContestEntryForOrganicUser() {
		List<UserEntries> userEntry;
		if (AppConfigLoader.env.equalsIgnoreCase("Qa")) {
			userEntry = ContestEntriesHelper.getContestEntriesByEmailQA(RegistrationPage.userEmail);
		} else {
			userEntry = ContestEntriesHelper.getContestEntriesByEmailSTG(RegistrationPage.userEmail);
		}
		return userEntry;
	}

	@Step
	public List<UserEntries> verifyContestEntryForUserCreatedFromRF() {
		List<UserEntries> userEntry;
		if (AppConfigLoader.env.equalsIgnoreCase("Qa")) {
			userEntry = ContestEntriesHelper.getContestEntriesByEmailQA(RegistrationPage.regGenerator.getEmail());
		} else {
			userEntry = ContestEntriesHelper.getContestEntriesByEmailSTG(RegistrationPage.regGenerator.getEmail());
		}
		return userEntry;
	}

	@Step
	public int getFailedContestEntryStatus(String status) throws IOException, SQLException {
		String gmt = RegistrationPage.regGenerator.getGmt();
		String query = "SELECT request_data,status FROM contest_entries;";
		LinkedList<LinkedHashMap<String, String>> resultSet = dbHelper.getMulitpleRowsAndColumnValues(query);
		for (LinkedHashMap<String, String> map : resultSet) {
			String requestGmt = WebServiceClient.getInstance().jsonParser(map.get("request_data"), "gmt");
			if (requestGmt != null && requestGmt.equalsIgnoreCase(gmt)) {
				return Integer.parseInt(map.get("status"));
			}
		}
		return -1;
	}

	@Step
	public int getContestEntryRecord() throws IOException, SQLException {
		String query = "select count(*) from contest_entries;";
		return Integer.parseInt(dbHelper.executeQuery(query));
	}
}
