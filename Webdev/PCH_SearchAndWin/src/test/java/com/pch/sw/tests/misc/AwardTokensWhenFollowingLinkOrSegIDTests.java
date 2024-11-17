package com.pch.sw.tests.misc;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.User;

/*
 * Promotion links using SegID through Emails tests
*/
public class AwardTokensWhenFollowingLinkOrSegIDTests extends BaseTest {
	
	private HomePage homePage;
	private WebHeaderPage headerPage;
	RegistrationPage regPage;
	private User randomUser_1;
	TokenHistoryPage tokenpage;
	
	@Test(description = "Award Tokens for Link Promotion.")
	public void testLinkPromotion() throws InterruptedException{
		
		homePage.load();
		loginToSearch(randomUser_1);
		
		// Reload the page with Promotion link
		headerPage.reloadPageUsingPromotionLink("test1808");
		headerPage.search("shoes", false);
		Common.sleepFor(5000);
		// Validate the tokens and the message
		homePage.goToTokenHistory();
		List<String> latestActivity=tokenpage.getActivityName();
	     Common.sleepFor(2000);
		//Assert.assertTrue(latestActivity.contains("From your Search&Win email"));
		Assert.assertTrue(latestActivity.contains("From your Search&Win email"));
		
	}
	
	@Test(dependsOnMethods="testLinkPromotion", description = "Do not Award Tokens for Link Promotion if already used.")
	public void testLinkPromotionReUse(){
		
		homePage.load();
		loginToSearch(randomUser_1);
		homePage.closeUserLevelLightBox();
		headerPage.search("shoes", false);
		
		// Reload the page with Promotion link
		headerPage.reloadPageUsingPromotionLink("test1808");
		headerPage.search("shoes", false);
		
		// Validate the tokens and the message
		String latestActivity = headerPage.getLatestActivityFromStatus();
		Assert.assertTrue(latestActivity.contains("You've already earned 7500tokens today!"));
		Assert.assertFalse(latestActivity.contains("From your Search&Win email"));
		Assert.assertFalse(latestActivity.contains("You've earned7500 Tokens!"));
		
	}
	
}
