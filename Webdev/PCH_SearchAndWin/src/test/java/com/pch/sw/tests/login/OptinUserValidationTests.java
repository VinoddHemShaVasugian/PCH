package com.pch.sw.tests.login;


import java.sql.SQLException;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;



import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.OptinLightBox;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class OptinUserValidationTests extends BaseTest {

	private HomePage webBasePage;
	private WebHeaderPage headerPage;
	public RegistrationPage webRegistrationPage;
	private AdminBasePage joomlaPage;
	private SearchResultsPage serp;
	private OptinLightBox optinLB;
	private String timeZone = "America/New_York";
	private User randomUnsubscribedUser_1,randomUnsubscribedUser_2,randomUnsubscribedUser_3,randomUnsubscribedUser_4,randomUnsubscribedUser_5, randomUnsubscribedUser_6, randomUnsubscribedUser_7;


	@Test(description="Validate if the Optin light box is shown as per the settings in admin.") 
	public void testOptinMessageFrequency() throws SQLException{
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 90000);
		String dateFormat = "MM/dd/yyyy";
		loginToSearchOptout(randomUnsubscribedUser_1);
		webBasePage.closeOptinLigthBox();
		webBasePage.closeUserLevelLightBox();
		headerPage.signOut();

		//Check for last week.
		String curentDate = Common.getCurrentDate(timeZone,dateFormat);
		String lastWeekDateSTr = Common.addDaysToDate(curentDate, dateFormat, -8);
		CustomLogger.log(lastWeekDateSTr);
		long lastWeekEpochTime = Common.getEpochTimeStamp(lastWeekDateSTr, dateFormat, timeZone);
		CustomLogger.log("string "+lastWeekEpochTime);
		DBUtils.updateLastLogin(randomUnsubscribedUser_1.getEmail(),lastWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_1);
		optinLB = webBasePage.getOptinLightBox();	
		Assert.assertTrue(optinLB.isLightBoxPresent());
		optinLB.dismissLightBox();
		headerPage.signOut();

		//Check for next week.
		String nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -15);
		long nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUnsubscribedUser_1.getEmail(),nextWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_1);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		optinLB.dismissLightBox();
		headerPage.signOut();

		//Check further next week		
		nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -22);
		nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUnsubscribedUser_1.getEmail(),nextWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_1);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		try{
			optinLB.dismissLightBox();
			CustomLogger.log("Dismissed optin box");
		}catch(NoSuchElementException e){
			CustomLogger.log("Didn't find optin box");
		}
		
		headerPage.signOut();

		//Check further next week
		nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -29);
		nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUnsubscribedUser_1.getEmail(),nextWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_1);
		Assert.assertFalse(optinLB.isLightBoxPresent());		
	}

	@Test(description="Test case id -24097 Change default time out in joomla for optin message")
	public void testOptinMessageTimeOut(){
		long timeOut = 15000;
		long timeOutReset = 60000;
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", timeOut);
		loginToSearchOptout(randomUnsubscribedUser_2);
		Common.sleepFor(20000);
		optinLB=webBasePage.getOptinLightBox();
		Assert.assertFalse(optinLB.isLightBoxPresent());
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", timeOutReset);
	}

	@Test(description="Validate dual Optin light box functionality.") 
	public void testDualOptinLightbox() throws SQLException{
		
		// Update the optin lightbox timeout in admin to 60secs
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 99999);
		String dateFormat = "MM/dd/yyyy";
		
		// Register user with optins not checked-in on Reg page
		loginToSearchOptout(randomUnsubscribedUser_3);
		
		webBasePage.closeUserLevelLightBox();
		
		// Validate if the dual optin light box is showed with two optin options
		optinLB = webBasePage.getOptinLightBox();
		Assert.assertTrue(optinLB.isLightBoxPresent());
		Assert.assertTrue(optinLB.isSearchOptinPresent());
		Assert.assertTrue(optinLB.isPchOptinPresent());
		
		webBasePage.closeOptinLigthBox();
		webBasePage.closeUserLevelLightBox();
		Assert.assertFalse(headerPage.getOptins().contains("BLINGO=22"));
		Assert.assertFalse(headerPage.getOptins().contains("PCHCOM=2"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		// Reset DB values to show LB again & Check S&W  Optin.
		String curentDate = Common.getCurrentDate(timeZone,dateFormat);
		String lastWeekDateSTr = Common.addDaysToDate(curentDate, dateFormat, -8);
		CustomLogger.log(lastWeekDateSTr);
		long lastWeekEpochTime = Common.getEpochTimeStamp(lastWeekDateSTr, dateFormat, timeZone);
		CustomLogger.log("string "+lastWeekEpochTime);
		DBUtils.updateLastLogin(randomUnsubscribedUser_3.getEmail(),lastWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_3);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		
		optinLB.checkSearchOptin();
		optinLB.clickSubmitButton();
		
		// Validate if only S&W optin is subscribed
		Assert.assertTrue(headerPage.getOptins().contains("BLINGO=22"));
		Assert.assertFalse(headerPage.getOptins().contains("PCHCOM=2"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		//Check for next week.
		String nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -15);
		long nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUnsubscribedUser_3.getEmail(),nextWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_3);
		
		// Validate if only PCHCom optin is showed on LB
		Assert.assertTrue(optinLB.isLightBoxPresent());
		Assert.assertTrue(optinLB.isPchOptinPresent());
		//Assert.assertFalse(optinLB.isSearchOptinPresent());
		
		
		// Validate if PCHCom optin is subscribed
		optinLB.checkPchOptin();
		optinLB.clickSubmitButton();
		Assert.assertTrue(headerPage.getOptins().contains("BLINGO=25"));
		Assert.assertTrue(headerPage.getOptins().contains("PCHCOM=2"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		// Reset DB values and check if the Optin is showed.
		DBUtils.updateLastLogin(randomUnsubscribedUser_3.getEmail(),lastWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchOptout(randomUnsubscribedUser_3);
		Assert.assertFalse(optinLB.isLightBoxPresent());
		
	}
	
	@Test(description="Validate dual Optin light box functionality. Scenario #1 - S&W optin on Reg page.") 
	public void testPartialOptinOnRegPage1() throws SQLException{
		
		// Update the optin lightbox timeout in admin to 60secs
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 90000);
		String dateFormat = "MM/dd/yyyy";
		
		// Register user with S&W optin on Reg page
		loginToSearchSnWOptIn(randomUnsubscribedUser_4);
		
		webBasePage.closeUserLevelLightBox();
				
		// Validate if optin light box is shown
		optinLB = webBasePage.getOptinLightBox();
		Assert.assertTrue(optinLB.isLightBoxPresent());
		Assert.assertTrue(optinLB.isPchOptinPresent());
		
		webBasePage.closeOptinLigthBox();
		webBasePage.closeUserLevelLightBox();
		Assert.assertTrue(headerPage.getOptins().contains("BLINGO=25"));
		Assert.assertFalse(headerPage.getOptins().contains("PCHCOM=2"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		// Reset DB values to show LB again & Check PCHCom  Optin.
		String curentDate = Common.getCurrentDate(timeZone,dateFormat);
		String lastWeekDateSTr = Common.addDaysToDate(curentDate, dateFormat, -8);
		CustomLogger.log(lastWeekDateSTr);
		long lastWeekEpochTime = Common.getEpochTimeStamp(lastWeekDateSTr, dateFormat, timeZone);
		CustomLogger.log("string "+lastWeekEpochTime);
		DBUtils.updateLastLogin(randomUnsubscribedUser_4.getEmail(),lastWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchSnWOptIn(randomUnsubscribedUser_4);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		
		optinLB.checkPchOptin();
		optinLB.clickSubmitButton();
		
		// Validate if both optins are subscribed
		Assert.assertTrue(headerPage.getOptins().contains("BLINGO=25"));
		Assert.assertTrue(headerPage.getOptins().contains("PCHCOM=2"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		//reset the date and validate the lightbox
		String nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -15);
		long nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUnsubscribedUser_3.getEmail(),nextWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchSnWOptIn(randomUnsubscribedUser_4);
		Assert.assertFalse(optinLB.isLightBoxPresent());
		
	}
	
	@Test(description="Validate dual Optin light box functionality. Scenario #2 - PCHCom optin on Reg page.") 
	public void testPartialOptinOnRegPage2() throws SQLException{
		
		// Update the optin lightbox timeout in admin to 60secs
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 90000);
		String dateFormat = "MM/dd/yyyy";
		
		// Register user with PCHCom optin Reg page
		loginToSearchPchComOptIn(randomUnsubscribedUser_5);
				
		webBasePage.closeUserLevelLightBox();
		
		// Validate if the optin light box is showed with Search&Win optin
		optinLB = webBasePage.getOptinLightBox();	
		Assert.assertTrue(optinLB.isLightBoxPresent());
		Assert.assertTrue(optinLB.isSearchOptinPresent());
		
		webBasePage.closeOptinLigthBox();
		webBasePage.closeUserLevelLightBox();
		Assert.assertFalse(headerPage.getOptins().contains("BLINGO=25"));
		Assert.assertTrue(headerPage.getOptins().contains("PCHCOM=5"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		// Reset DB values to show LB again & Check S&W  Optin.
		String curentDate = Common.getCurrentDate(timeZone,dateFormat);
		String lastWeekDateSTr = Common.addDaysToDate(curentDate, dateFormat, -8);
		CustomLogger.log(lastWeekDateSTr);
		long lastWeekEpochTime = Common.getEpochTimeStamp(lastWeekDateSTr, dateFormat, timeZone);
		CustomLogger.log("string "+lastWeekEpochTime);
		DBUtils.updateLastLogin(randomUnsubscribedUser_5.getEmail(),lastWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchPchComOptIn(randomUnsubscribedUser_5);
		Assert.assertTrue(optinLB.isLightBoxPresent());
		
		optinLB.checkSearchOptin();
		optinLB.clickSubmitButton();
		
		// Validate if both optins are subscribed
		Assert.assertTrue(headerPage.getOptins().contains("BLINGO=22"));
		Assert.assertTrue(headerPage.getOptins().contains("PCHCOM=5"));
		headerPage.gotoHome();
		headerPage.signOut();
		
		//reset the date and validate the lightbox
		String nextWeekDateStr = Common.addDaysToDate(curentDate, dateFormat, -15);
		long nextWeekEpochTime = Common.getEpochTimeStamp(nextWeekDateStr, dateFormat, timeZone);
		DBUtils.updateLastLogin(randomUnsubscribedUser_5.getEmail(),nextWeekEpochTime);		
		webBasePage.removeAllCookies();
		loginToSearchPchComOptIn(randomUnsubscribedUser_5);
		Assert.assertFalse(optinLB.isLightBoxPresent());
		
	}
	
//	@Test(description="Validate Lotto Optin functionality on SERP.") 
	public void testLottoOptinOnSerp() throws SQLException{
		
		// Register user
		loginToSearch(randomUnsubscribedUser_6);
		headerPage.search("PCH");
		
		// Validate if Lotto Optin is shown on SERP
		serp.validateLottoOptin();
		
		// Click on Optin Yes Button on the Ad
		serp.clickOnLottoOptinYesButton();
		
		// Validate Lotto Optin Confirmation
		serp.validateLottoOptinConfirmation();
		
		// Validate if subscribed for Lotto 
		Assert.assertTrue(headerPage.getOptins().contains("LOTTO=2"));
		headerPage.gotoHome();
		webBasePage.closeUserLevelLightBox();
		
		// Search for another keyword and Lotto OptIn shouldn't be displayed.
		headerPage.search("PCH2");
		
		// Validate if the Open-x Ad Image is displayed
		serp.validateOpenXImage();
	}
	
//	@Test(description="Validate Lotto Optin not displayed on SERP for Guest Users.") 
	public void testLottoOptinOnSerp_GuestUser() throws SQLException{
		
		// Go to Search&Win and search as a guest user
		webBasePage.load();
		headerPage.search("shoes", false);
		
		// Validate if Open-X image is shown on SERP
		serp.validateOpenXImage();
		
	}
	
//	@Test(description="Validate Lotto Optin is shown only once per day.") 
	public void testLottoOptinShownOncePerDay() throws SQLException{
		
		// Register user
		loginToSearch(randomUnsubscribedUser_7);
		headerPage.search("shoes", false);
		
		// Validate if Lotto Optin is shown on SERP
		serp.validateLottoOptin();
		
		// Search again and check if Lotto OptIn shouldn't be displayed.
		headerPage.search("shoe", false);
		serp.validateOpenXImage();
		
		// Reset DB values to validate Optin is showed again
		DBUtils.expireDailySearch(randomUnsubscribedUser_7.getEmail());
		DBUtils.deleteLottoOptinProperty(randomUnsubscribedUser_7.getEmail());
		
		// Validate Lotto Optin
		headerPage.search("shoes", false);
		serp.validateLottoOptin();
		
	}
	
	@Test(description = "Validate dual Optin light box functionality.")
	public void testOptinLightbox() throws SQLException {

		// Update the optin lightbox timeout in admin to 60secs
		joomlaPage.goToArticle("Optin Lightbox");
		joomlaPage.setTextProperty("Timeout", 90000);
//		String dateFormat = "MM/dd/yyyy";
		// Register user with optins not checked-in on Reg page
		loginToSearchOptout(randomUnsubscribedUser_6);
		// Validate if the dual optin light box is showed with single optin
		// options
		webBasePage.closeUserLevelLightBox();
		optinLB = webBasePage.getOptinLightBox();
		Assert.assertTrue(optinLB.isLightBoxPresent());
		Assert.assertTrue(optinLB.isSearchOptinPresent());
		webBasePage.closeOptinLigthBox();
		Assert.assertFalse(headerPage.getOptins().contains("BLINGO=22"));
		Assert.assertFalse(headerPage.getOptins().contains("PCHCOM=2"));
		webBasePage.load();
		headerPage.signOut();

	}
}