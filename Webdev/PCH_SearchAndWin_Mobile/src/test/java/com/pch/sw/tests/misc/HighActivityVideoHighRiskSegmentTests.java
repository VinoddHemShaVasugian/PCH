package com.pch.sw.tests.misc;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class HighActivityVideoHighRiskSegmentTests extends BaseTest {
	private HomePage homePage;
	public WebHeaderPage headerPage;
	private AdminBasePage joomlaPage;
	RegistrationPage regPage;
	private SearchResultsPage searchResultsPage;
	private CentralServicesPage centralServicesPage;
	private User randomUser_1;

	@Test(testName = "TestID=21279", description = "Add user to the same segment as the video and validate user is able to watch it")
	public void test_HighRiskVideoForSegmentUser_FirstTime() throws SQLException {

		joomlaPage.goToArticle("Video - SERP - High Risk");
		// get selected segment
		String Selectedsegment = joomlaPage.getSelectedSegment("group_1_segments_included");
		// select segment "ACTIVE_FP_L60" if nothing is selected
		if (!Selectedsegment.equals("CUSTOMER_VIP")) {
			joomlaPage.selectSegmentByText("group_1_segments_included", "CUSTOMER_VIP");
		}
		joomlaPage.saveCloseAndClearCache();
		// login and perform search
		loginToSearch(randomUser_1);
		// validate video should not display at the SERP
		homePage.closeUserLevelLightBox();
		Assert.assertFalse(searchResultsPage.isHighRiskVideoExists(), "High risk video is displayed");

		// ad user into segment
		centralServicesPage.navigateToSegmentMembershipPage();
		centralServicesPage.setSegmentMembership(randomUser_1.getEmail(), "CUSTOMER_VIP");

		// Update database, add user to the segment
		CustomLogger.log("Going to update DB...");
		DBUtils.updateSegmentsProperty(randomUser_1.getEmail(), "CUSTV");

		// login and perform search
		homePage.load();
		// validate video should not display at the SERP
		Assert.assertTrue(searchResultsPage.isHighRiskVideoExists(), "High risk video does not display");

	}

	@Test(testName = "TestID=21279", description = "Add user to the same segment as the video and validate user is able to watch it", dependsOnMethods = "test_HighRiskVideoForSegmentUser_FirstTime")
	public void test_HighRiskVideoForSegmentUser_SecondTime() throws SQLException {

		loginToSearch(randomUser_1);
		// validate video should not display at the SERP
		Assert.assertFalse(searchResultsPage.isHighRiskVideoExists(), "High risk video does not display");
		// update database remove entries for the video
		DBUtils.updateTokensForWatchingVideos(randomUser_1.getEmail(), "videoplayer_37");
		// validate video should display at the SER
		searchResultsPage.enterSearchKeyword("PCH1");
		Assert.assertTrue(searchResultsPage.isHighRiskVideoExists(), "High risk video does not display");
	}
}
