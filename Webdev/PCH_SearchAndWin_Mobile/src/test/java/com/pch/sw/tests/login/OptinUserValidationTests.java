package com.pch.sw.tests.login;

import java.sql.SQLException;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.OptinLightBox;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class OptinUserValidationTests extends BaseTest {

	private HomePage webBasePage;
	private WebHeaderPage headerPage;
	public RegistrationPage webRegistrationPage;
	private AdminBasePage joomlaPage;
	private OptinLightBox optinLB;
	private String timeZone = "America/New_York";
	private HomePage homePage;
	private User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5;

	@Test( description = "Validate if the Optin light box is shown as per the settings in admin.")
	public void testOptinMessageFrequency() throws SQLException {
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 90000);
		String dateFormat = "MM/dd/yyyy";
		loginToSearchOptout(randomUser_1);
		webBasePage.closeOptinLigthBox();
		webBasePage.closeUserLevelLightBox();
		headerPage.signOut();

		// Check for last week.
		String curentDate = Common.getCurrentDate(timeZone, dateFormat);
		String lastWeekDateSTr = Common.addDaysToDate(curentDate, dateFormat, -8);
		CustomLogger.log(lastWeekDateSTr);
		long lastWeekEpochTime = Common.getEpochTimeStamp(lastWeekDateSTr, dateFormat, timeZone);
		CustomLogger.log("string " + lastWeekEpochTime);
		DBUtils.updateLastLogin(randomUser_1.getEmail(), lastWeekEpochTime);
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUser_1);
		optinLB = webBasePage.getOptinLightBox();
		Assert.assertTrue(optinLB.isLightBoxPresent());
		optinLB.dismissLightBox();
		headerPage.signOut();

		// Check for next week.
		String nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -15);
		long nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUser_1.getEmail(), nextWeekEpochTime);
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUser_1);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		optinLB.dismissLightBox();
		headerPage.signOut();

		// Check further next week
		nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -22);
		nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUser_1.getEmail(), nextWeekEpochTime);
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUser_1);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		try {
			optinLB.dismissLightBox();
			CustomLogger.log("Dismissed optin box");
		} catch (NoSuchElementException e) {
			CustomLogger.log("Didn't find optin box");
		}

		headerPage.signOut();

		// Check further next week
		nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -29);
		nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUser_1.getEmail(), nextWeekEpochTime);
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUser_1);
		Assert.assertFalse(optinLB.isLightBoxPresent());
	}

	@Test(description = "Test case id -24097 Change default time out in joomla for optin message")
	public void testOptinMessageTimeOut() {
		long timeOut = 10000;
		long timeOutReset = 60000;
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", timeOut);
		loginToSearchOptout(randomUser_2);
		homePage.closeUserLevelLightBox();
		Common.sleepFor(20000);
		optinLB = webBasePage.getOptinLightBox();
		Assert.assertFalse(optinLB.isLightBoxPresent());
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", timeOutReset);
	}

	@Test( description = "Validate dual Optin light box functionality.")
	public void testOptinLightbox() throws SQLException {

		// Update the optin lightbox timeout in admin to 60secs
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 90000);
//		String dateFormat = "MM/dd/yyyy";
		// Register user with optins not checked-in on Reg page
		loginToSearchOptout(randomUser_3);
		// Validate if the dual optin light box is showed with single optin
		// options
		homePage.closeUserLevelLightBox();
		optinLB = webBasePage.getOptinLightBox();
		Assert.assertTrue(optinLB.isLightBoxPresent());
		Assert.assertTrue(optinLB.isSearchOptinPresent());
		webBasePage.closeOptinLigthBox();
		Assert.assertFalse(headerPage.getOptins().contains("BLINGO=22"));
		Assert.assertFalse(headerPage.getOptins().contains("PCHCOM=2"));
		homePage.load();
		headerPage.signOut();

	}
}