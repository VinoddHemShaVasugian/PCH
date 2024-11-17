package com.pch.sw.tests.misc;

import java.sql.SQLException;

import org.openqa.selenium.UnhandledAlertException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class SetupAdminConfigurationComponent extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	TokenHistoryPage tokenHistoryPage;
	SearchResultsPage serp;
	User randomUser_1,randomUser_2,randomUser_3,randomUser_4,randomUser_5;
    String maxTokensEarnedAlertText = "You have reached your maximum DAILY chances to WIN instant prizes or score tokens by searching. Remember, failure to conduct legitimate searches may result in your account privileges being revoked.";
	String additionalHrAlertsText = "*ALERT* Are you sure you want to continue? Please keep in mind that failure to conduct legitimate searches may result in your account privileges being revoked.";

    @Test(description="unrecognised user forces to sign-in",priority =2)
	public void unrecMaxSearchesforcesLogin(){
		joomlaPage.gotoPCHConfigurationTab("Infospace");
		joomlaPage.max_searches_before_login().clear();
		joomlaPage.max_searches_before_login().sendKeys("3");
		joomlaPage.saveCloseAndClearCache();
		homePage.load();
		for(int i=1;i<=3;i++){
			System.out.print("Search count is:: " + i);
			headerPage.multipleSearch("PCH");
		}
		Common.sleepFor(4000);
		Assert.assertTrue(homePage.getCurrentURL().contentEquals("https://accounts." + Environment.getEnvironment() + ".pch.com/login"));
		CustomLogger.log("SERP is automatically redirected to login page on 3rd search.");
		CustomLogger
				.log("Current page url is : " + "https://accounts." + Environment.getEnvironment() + ".pch.com/login");
		}
		
	@Test(description=" first & second warning alert for recognised user",priority=1)
	public void AlertWarning(){
		joomlaPage.gotoPCHConfigurationTab("Infospace");
		CustomLogger.log("config change for first and later warnings");
		joomlaPage.search_warnings_first().clear();
		joomlaPage.search_warnings_first().sendKeys("3");
		joomlaPage.search_warnings_later().clear();
		joomlaPage.search_warnings_later().sendKeys("5");
		joomlaPage.saveCloseAndClearCache();
		loginToSearch(randomUser_1);
		for(int i=1;i<=5;i++){
			System.out.print("Search count is:: " + i);
			headerPage.multipleSearch("PCH");
			if (i==3){
				Assert.assertTrue(serp.isAlertPresent());
			    CustomLogger.log("Search first alert is displayed.");
				Assert.assertTrue(serp.getAlertText().contains(additionalHrAlertsText));
				serp.dismissAlert();
				}
			if(i==5){
				Assert.assertTrue(serp.isAlertPresent());
				CustomLogger.log("Search second alert is displayed.");
				Assert.assertTrue(serp.getAlertText().contains(additionalHrAlertsText));
				// Dismiss alert to land on home page
				serp.dismissAlert();
			}
			}
		}
	
	    //@Test(description="recognised user shows for additional warning",priority=3)
		public void additionalSearchWarning(){
			joomlaPage.gotoPCHConfigurationTab("Infospace");
			joomlaPage.aditional_search_warnings().clear();
			joomlaPage.aditional_search_warnings().sendKeys("6");
			joomlaPage.saveCloseAndClearCache();
			loginToSearch(randomUser_2);
			for(int i=1;i<=6;i++){
				System.out.print("Search count is:: " + i);
				headerPage.multipleSearch("PCH");
			}
			Assert.assertTrue(serp.isAlertPresent());
			Assert.assertTrue(serp.getAlertText().contains(maxTokensEarnedAlertText));
			CustomLogger.log("Alert  lightbox should be doisplayed.");
			serp.dismissAlert();
		   }
		
       @Test(description="algo page for recognised user",priority=3)
		public void algoOnlySearchesRecognized() throws SQLException{
			String user = "np21@pchmail.com";
			joomlaPage.gotoPCHConfigurationTab("Infospace");
		    joomlaPage.algo_only_searches_recognized().clear();
			joomlaPage.algo_only_searches_recognized().sendKeys("3");
			joomlaPage.saveCloseAndClearCache();
		    homePage.load();
		    headerPage.loginToSearch(user, "testing");
			for(int i=1;i<=3;i++){
				System.out.print("Search count is:: " + i);
				headerPage.multipleSearch("PCH");
			}
			serp.dismissAlert();
		   serp.validateNFSP("source=mx_m");
			serp.validateSegment("segment=pchmaxclick.pchmaxclick.mobile");
			CustomLogger.log("NFSP segment has been changed to 'mx_m' which is algo only segment.");
		   }
		@Test(description="algo page for unrecognised user",priority=4)
		public void algoOnlySearchesUnRecognized() throws SQLException{
	       joomlaPage.gotoPCHConfigurationTab("Infospace");
			joomlaPage.algo_only_searches_unrecognized().clear();
			joomlaPage.algo_only_searches_unrecognized().sendKeys("2");
			joomlaPage.saveCloseAndClearCache();
			homePage.load();
		     for(int i=1;i<=2;i++){
				System.out.print("Search count is:: " + i);
				headerPage.multipleSearch("PCH");
			}
		    Common.sleepFor(2000);
		    serp.validateNFSP("source=mx_m");
			serp.validateSegment("segment=pchmaxclick.pchmaxclick.mobile");
			CustomLogger.log("NFSP segment has been changed to 'mx_m' which is algo only segment.");
			}
		@Test(description="changing values",priority=5)
		public void updatingoriginalValues(){
			joomlaPage.gotoPCHConfigurationTab("Infospace");
			joomlaPage.search_warnings_first().clear();
			joomlaPage.search_warnings_first().sendKeys("40");
			joomlaPage.search_warnings_later().clear();
			joomlaPage.search_warnings_later().sendKeys("75");
	        joomlaPage.aditional_search_warnings().clear();
		    joomlaPage.aditional_search_warnings().sendKeys("25");
	        joomlaPage.algo_only_searches_recognized().clear();
			joomlaPage.algo_only_searches_recognized().sendKeys("27");
	        joomlaPage.algo_only_searches_unrecognized().clear();
			joomlaPage.algo_only_searches_unrecognized().sendKeys("6");
			joomlaPage.saveCloseAndClearCache();
	   }

	}

