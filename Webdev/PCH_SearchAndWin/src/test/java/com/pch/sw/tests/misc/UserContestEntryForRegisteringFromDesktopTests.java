package com.pch.sw.tests.misc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.lightBox.CreatePasswordLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class UserContestEntryForRegisteringFromDesktopTests extends BaseTest {
	private HomePage homePage;
	private WebHeaderPage headerPage;
	CentralServicesPage csPage;
	RegistrationPage regPage;
	private User randomUser_1, randomUser_2, randomUser_3;
	Hashtable<String, String> columnValues = new Hashtable<String, String>();
	int columnsNumber;
	ResultSet rs;
	ResultSetMetaData rsmd;
	Calendar cal = Calendar.getInstance();
	String mySqlDate = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
	String sqlDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

	@Test(description = "TestID='16551'_Verify contest entry in the DB(SQL/MySql) for registring an user from desktop", groups = {
			GroupNames.Desktop, GroupNames.Regression })
	public void testContestEntryForFullRegistation() throws SQLException, ClassNotFoundException {
		// initialize hash table for expected DB values
		// STG
		/*
		 * columnValues.put("Source", "AD481"); columnValues.put("Created",
		 * sqlDate); columnValues.put("TargetCodeId", "201324892085907");
		 * columnValues.put("pdate", mySqlDate); columnValues.put("tc",
		 * "2085WS907P"); columnValues.put("v", "20132489");
		 */
		// QA

		columnValues.put("Source", "PC012");
		columnValues.put("Created", sqlDate);
		columnValues.put("TargetCodeId", "201531112067744");
		columnValues.put("pdate", mySqlDate);
//		columnValues.put("tc", "206WS7744P"); // old: 206WS7744P
//		columnValues.put("v", "20153111"); // old: 20153111
		columnValues.put("tc", "2044ws880p"); // old: 206WS7744P
		columnValues.put("v", "20073489"); // old: 20153111

		// register user
		loginToSearch(randomUser_1);
		// String sqlQuery = "SELECT Source,Created,TargetCodeId from
		// BillMeEntries where Email ='"
		// + randomUser_1.getEmail() + "';";
		String mySqlQuery = "SELECT pdate,tc,v FROM `sandw_superprize_queue` where email='" + randomUser_1.getEmail()
				+ "';";

		// validate contest entry in the DataBase
		Assert.assertTrue(validateContestEntry(mySqlQuery), "No Record found in any of the Database");
		validateContestEntry();

		// Do the first search and validate entry in DB
		headerPage.search("pch", false);
		Assert.assertTrue(validateContestEntry(mySqlQuery), "Data not found in any of the DataBase");
		validateEntryForSearch();

	}

	@Test(description = "TestID='16551'_Verify contest entry in the DB(SQL/MySql) for Silver user from desktop", groups = {
			GroupNames.Desktop, GroupNames.Regression })
	public void testContestEntryForSilverUserRegistration() throws SQLException {
		columnValues.put("Source", "ae743");
		columnValues.put("Created", sqlDate);
		columnValues.put("TargetCodeId", "201328892086633");
		columnValues.put("pdate", mySqlDate);
		columnValues.put("tc", "208mu6633w");
		columnValues.put("v", "20132889");
		columnValues.put("search_tc", "208MU6633W");
		columnValues.put("search_v", "20132889");

		String sqlQuery = "SELECT Source,Created,TargetCodeId from BillMeEntries where Email ='"
				+ randomUser_2.getEmail() + "';";
		String mySqlQuery = "SELECT pdate,tc,v FROM `sandw_superprize_queue` where email='" + randomUser_2.getEmail()
				+ "'";

		// Create a user without password
		String registrationURL = csPage.createSilverUser(randomUser_2);
		Assert.assertNotNull(registrationURL);
		System.out.println(registrationURL);
		Assert.assertFalse(validateContestEntry(sqlQuery, mySqlQuery), "Data found in one of the DataBase");

		// complete registration
		homePage.load(registrationURL);
		headerPage.tokenCenterCompleteRegBtn().click();
		CustomLogger.log("Completing registration.");
		CreatePasswordLightBox cpl = homePage.createPasswordLightBox();
		cpl.enterPasswordandConfirm(randomUser_2.getPassword());
		homePage.closeUserLevelLightBox();

		// validate contest entry in the DataBase
		Assert.assertTrue(validateContestEntry(sqlQuery, mySqlQuery), "Data not found in any of the DataBase");
		validateContestEntry();

		// Do the first search and validate entry in DB
		headerPage.search("shoes", false);
		Assert.assertTrue(validateContestEntry(sqlQuery, mySqlQuery), "Data not found in any of the DataBase");
		validateEntryForSearch();

	}

	@Test(description = "TestID='16551'_Verify contest entry in the DB(SQL/MySql) for Silver user from desktop", groups = {
			GroupNames.Desktop, GroupNames.Regression })
	public void testContestEntryForMiniRegUserRegistration() throws SQLException {
		// STG
		/*
		 * columnValues.put("Source", "AD481"); columnValues.put("Created",
		 * sqlDate); columnValues.put("TargetCodeId", "201324892085907");
		 * columnValues.put("pdate", mySqlDate); columnValues.put("tc",
		 * "2085WS907P"); columnValues.put("v", "20132489");
		 */
		// QA

		columnValues.put("Source", "PC012");
		columnValues.put("Created", sqlDate);
		columnValues.put("TargetCodeId", "201531112067744");
		columnValues.put("pdate", mySqlDate);
		columnValues.put("tc", "206WS7744P");
		columnValues.put("v", "20153111");

		String sqlQuery = "SELECT Source,Created,TargetCodeId from BillMeEntries where Email ='"
				+ randomUser_3.getEmail() + "';";
		String mySqlQuery = "SELECT pdate,tc,v FROM `sandw_superprize_queue` where email='" + randomUser_3.getEmail()
				+ "'";

		// Create a user without password
		String registrationURL = csPage.createMiniRegUser(randomUser_3);
		Assert.assertNotNull(registrationURL);
		System.out.println(registrationURL);
		Assert.assertFalse(validateContestEntry(sqlQuery, mySqlQuery), "Data found in one of the DataBase");

		loginToSearchAsMiniregUser(randomUser_3);
		Assert.assertTrue(headerPage.isCompleteRegBtnExists(),
				"Complete registration button does not exists on the page");
		headerPage.tokenCenterCompleteRegBtn().scrollUpAndClick();
		CustomLogger.log("Landed on Registration page.");

		regPage.enterMiniregUserDetails(randomUser_3);
		regPage.clickSubmitButton();

		homePage.closeUserLevelLightBox();

		String welcomeText = headerPage.getWelcomeUserLinkText();
		Assert.assertTrue(
				welcomeText.startsWith("Welcome " + randomUser_3.getFirstname() + " " + randomUser_3.getLastname()));
		Assert.assertFalse(headerPage.isCompleteRegBtnExists(), "Complete registration button exists on the page");

		// validate contest entry in the DataBase
		// Assert.assertTrue(validateContestEntry(sqlQuery, mySqlQuery), "Data
		// not found in any of the DataBase");
		validateContestEntry();

		// Do the first search and validate entry in DB
		headerPage.search("pch", false);
		Assert.assertTrue(validateContestEntry(sqlQuery, mySqlQuery), "Data not found in any of the DataBase");
		validateEntryForSearch();

	}

	/**
	 * This function is used for validating contest entry inside the table
	 * 
	 * @return: True if record is found in any of the DB(Sql/MySql), False
	 *          otherwise
	 * @param: Sql
	 *             query, MySql query
	 */
	public boolean validateContestEntry(String sqlQuery, String mySqlQuery) {
		boolean dataFound = false;
		try {
			CustomLogger.log("Going to find record in SQL Server Database");
			rs = DBUtils.executeSQLQuery(sqlQuery);
			if (rs.next()) {
				CustomLogger.log("Record found in SQL Server Database");
				dataFound = true;
			} else {
				CustomLogger.log("No record found in SQL Server Database");
				CustomLogger.log("Going to find record in MySQL Database");
				rs = DBUtils.executeQueryAndReturnResultSet(mySqlQuery);
				if (rs.next()) {
					CustomLogger.log("Record found in MySQL Database");
					dataFound = true;
				} else {
					CustomLogger.log("No record found in MySql Database");
				}
			}
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}
		return dataFound;
	}

	/**
	 * This function is used for validating contest entry inside the table
	 * 
	 * @return: True if record is found in any of the DB(Sql/MySql), False
	 *          otherwise
	 * @param: MySql
	 *             query
	 */
	public boolean validateContestEntry(String mySqlQuery) {
		boolean dataFound = false;
		try {
			CustomLogger.log("Going to find record in MySQL Database");
			rs = DBUtils.executeQueryAndReturnResultSet(mySqlQuery);
			if (rs.next()) {
				CustomLogger.log("Record found in MySQL Database");
				dataFound = true;
			} else {
				CustomLogger.log("No record found in MySql Database");
			}
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}
		return dataFound;
	}

	/**
	 * This function is used for validating record inside the table
	 **/
	public void validateContestEntry() throws SQLException {
		try {
			CustomLogger.log("Going to validate an entry in DB for registration");
			rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			rs.beforeFirst();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String expected = columnValues.get(rsmd.getColumnName(i)).toLowerCase();
					String actual = rs.getString(i).split(" ")[0].toString().toLowerCase();
					CustomLogger.log("Compairing '" + expected + "' and '" + actual + "'");
					Assert.assertEquals(actual, expected);
				}
			}
			CustomLogger.log("Entry found in DB for registration");
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}
	}

	/**
	 * This function is used for validating contest entry record for first
	 * search inside the table
	 */
	public void validateEntryForSearch() throws SQLException {
		// STG
		/*
		 * columnValues.put("Source", "ae743"); columnValues.put("Created",
		 * sqlDate); columnValues.put("TargetCodeId", "201328892086633");
		 * columnValues.put("pdate", mySqlDate); columnValues.put("tc",
		 * "2085ws907p"); columnValues.put("v", "20134531");
		 */
		// QA
		columnValues.put("Source", "PC014");
		columnValues.put("Created", sqlDate);
		columnValues.put("TargetCodeId", "201531112067747");
		columnValues.put("pdate", mySqlDate);
		columnValues.put("tc", "2044ws880p"); // old: 206WS7744P
		columnValues.put("v", "20073489"); // old: 20153111

		try {
			CustomLogger.log("Going to validate an entry in DB for first search");
			rsmd = rs.getMetaData();
			columnsNumber = rsmd.getColumnCount();
			rs.beforeFirst();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String expected = columnValues.get(rsmd.getColumnName(i)).toLowerCase();
					String actual = rs.getString(i).split(" ")[0].toString().toLowerCase();
					CustomLogger.log("Compairing '" + expected + "' and '" + actual + "'");
					Assert.assertEquals(actual, expected);
					if (i == 3) {
						return;
					}
				}
			}
			CustomLogger.log("Entry for first search found in DB");
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}
	}

}
