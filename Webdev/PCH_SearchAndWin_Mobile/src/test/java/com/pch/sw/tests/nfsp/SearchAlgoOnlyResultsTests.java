package com.pch.sw.tests.nfsp;

import java.sql.SQLException;

import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class SearchAlgoOnlyResultsTests extends BaseTest {
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage serp;
	User randomUser_1, randomUser_2, randomUser_3;

	@Test(testName = "17347 - Algo_Rec_UnrecUsers_Mobile", description = "Nfsp segment should change to algo only after 5 searches for guest users.")
	public void testAlgoOnlyResults_MobileGuestUser() throws SQLException {

		homePage.load();
		String user = headerPage.getCookieValues("pci");
		CustomLogger.log("User cookie values is : " + user);

		headerPage.search("shoes", false);
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");

		// Validate if the nfsp is changed to algo only after 5 searches
		DBUtils.updateSearchCount_GuestUser(user, 5);
		headerPage.search(Common.getRandomString(5), false);

		serp.validateNFSP("source=mx_m");
		serp.validateSegment("segment=pchmaxclick.pchmaxclick.mobile");
		CustomLogger.log("NFSP segment has been changed to 'mx_m' which is algo only segment.");
	}

	@Test(testName = "17347 - Algo_Rec_UnrecUsers_Mobile", description = "Nfsp segment should change to algo only after 26 searches for registered users.")
	public void testAlgoOnlyResults_MobileRegisteredUser() throws SQLException {

		String user = "kcheguri21@pchmail.com";

		homePage.load();
		DBUtils.expireDailySearch(user);
		headerPage.loginToSearch(user, "testing");

		CustomLogger.log("User Email ID is : " + user);

		headerPage.search("shoes", false);
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");

		// Validate if the nfsp is changed to algo only after 5 searches
		DBUtils.updateSearchCount(user, 26);
		headerPage.search(Common.getRandomString(5), false);

		serp.validateNFSP("source=mx_m");
		serp.validateSegment("segment=pchmaxclick.pchmaxclick.mobile");

		CustomLogger.log("NFSP segment has been changed to 'mx_m' which is algo only segment.");
	}
}
