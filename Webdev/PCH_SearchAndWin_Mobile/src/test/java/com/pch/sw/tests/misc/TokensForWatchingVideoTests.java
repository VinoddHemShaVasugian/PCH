package com.pch.sw.tests.misc;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.AboutPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.MyAccountPage;
import com.pch.search.pages.web.MyAccountPage.MyAccountTab;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class TokensForWatchingVideoTests extends BaseTest {

	private HomePage homePage;
	public WebHeaderPage headerPage;
	private AdminBasePage joomlaPage;
	RegistrationPage regPage;
	private AboutPage aboutPage;
	private MyAccountPage myAccountPage;
	private TokenHistoryPage tokenHistoryPage;
	private User randomUser_1;
	String dateFormat = "yyyy-MM-dd";
	String timeZone = "America/New_York";
	String currentDate = Common.getCurrentDate(timeZone, dateFormat);

	@Test(enabled = false)
	public void tokensForWatchVideoConfiguration() {
		joomlaPage.goToArticle("Prize Machine / Tokens / Watching Video");
		Assert.assertEquals(joomlaPage.setTextProperty("Token Amount", 50), "success");
	}

	@Test(testName = "Test ID=22126", description = "verify that user get tokens to watch full videos for the first time")
	public void testTokensForWatchingVideos() throws InterruptedException {

		// Login to pch search and move to about pch search page
		loginToSearch(randomUser_1);
		headerPage.menu().click();
		homePage.goToAboutPCHSearchAndWinPage();
		// validate video exists
		Assert.assertTrue(aboutPage.isVideoExists("videoClip"), "Video does not display");
		Common.sleepFor(3000);
		// do not watch complete video
		homePage.goToAboutTokens();
		Assert.assertTrue(aboutPage.isVideoExists("tokenVideo"), "Video does not display");
		Common.sleepFor(3000);
		// do not watch complete video and navigate to myAccount page and
		// validate user should not get tokens
		homePage.goToTokenHistory();//goToMyAccountPage();
//		myAccountPage.navigateToTab(MyAccountTab.Token_History);
		Assert.assertFalse(tokenHistoryPage.getActivityName().contains("For Watching the Video!"));

		// navigate to about pch page and watch complete video and validate user
		// should get the tokens
		homePage.goToHomePage();
		homePage.goToAboutPCHSearchAndWinPage();
		Assert.assertTrue(aboutPage.isVideoExists("videoClip"), "Video does not display");
		aboutPage.waitTillGetTheTokens();

		// navigate to about tokens page and watch complete video and validate
		// tokens in token history
		homePage.goToAboutTokens();
		Assert.assertTrue(aboutPage.isVideoExists("tokenVideo"), "Video does not display");
		aboutPage.waitTillGetTheTokens();
		homePage.goToMyAccountPage();
		myAccountPage.navigateToTab(MyAccountTab.Token_History);
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains("For Watching the Video!"));
		Assert.assertTrue(tokenHistoryPage.getEventCount("For Watching the Video!") == 2);
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent("For Watching the Video!"), String.valueOf(50));

	}

	@Test(testName = "Test ID=22126", dependsOnMethods = "testTokensForWatchingVideos", description = "Verify that user get tokens for watching same videos again next day")
	public void testTokensForWatchingVideosNextDay() throws InterruptedException, SQLException {

		loginToSearch(randomUser_1);
		// watch same video again and should not get tokens
		homePage.goToAboutPCHSearchAndWinPage();
		Assert.assertTrue(aboutPage.isVideoExists("videoClip"), "Video does not display");
		aboutPage.waitTillGetTheTokens();
		homePage.goToMyAccountPage();
		myAccountPage.navigateToTab(MyAccountTab.Token_History);
		Assert.assertTrue(tokenHistoryPage.getEventCount("For Watching the Video!") < 3);

		// update database
		DBUtils.updateTokensForWatchingVideos(randomUser_1.getEmail(), "token_awarded_watchingvideo_about");
		DBUtils.updateTokensForWatchingVideos(randomUser_1.getEmail(), "token_awarded_watchingvideo_tokens");

		// navigate to about pch page and watch complete video and validate user
		// should get the tokens
		homePage.goToHomePage();
		homePage.goToAboutPCHSearchAndWinPage();
		Assert.assertTrue(aboutPage.isVideoExists("videoClip"), "Video does not display");
		aboutPage.waitTillGetTheTokens();

		// navigate to about tokens page and watch complete video and validate
		// tokens in token history
		homePage.goToAboutTokens();
		Assert.assertTrue(aboutPage.isVideoExists("tokenVideo"), "Video does not display");
		aboutPage.waitTillGetTheTokens();
		homePage.goToMyAccountPage();
		myAccountPage.navigateToTab(MyAccountTab.Token_History);
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains("For Watching the Video!"));
		Assert.assertTrue(tokenHistoryPage.getEventCount("For Watching the Video!") == 4);
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent("For Watching the Video!"), String.valueOf(50));

	}
}
