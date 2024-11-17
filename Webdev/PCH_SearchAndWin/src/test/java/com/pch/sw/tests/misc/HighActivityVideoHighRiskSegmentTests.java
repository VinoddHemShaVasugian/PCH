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

		//get selected segment
		//String Selectedsegment=joomlaPage.getSelectedSegment("group_1_segments_included");		
		//select segment "ACTIVE_FP_L60" if nothing is selected 
		//joomlaPage.unselectallsegments("");
	     //if(!Selectedsegment.equals("CUSTOMER_VIP")){
			joomlaPage.getSelectedSegment("CUSTOMER_VIP");			
		

		// get selected segment
		String Selectedsegment = joomlaPage.getSelectedSegment("pch_video_player_group_0_segments_included");
		// select segment "ACTIVE_FP_L60" if nothing is selected
		if (!Selectedsegment.equals("ACQ_CustomerVIP_MAILABLE")) {
			joomlaPage.selectSegmentByText("pch_video_player_group_0_segments_included", "ACQ_CustomerVIP_MAILABLE");
		}
		joomlaPage.saveCloseAndClearCache();
		centralServicesPage.createFullRegAndGetGMT(randomUser_1);

		// login and perform search
		loginToSearch(randomUser_1);
		// validate video should not display at the SERP
		Assert.assertFalse(searchResultsPage.isHighRiskVideoExists("PCH"), "High risk video does not display");

		// ad user into segment
		centralServicesPage.navigateToSegmentMembershipPage();
		centralServicesPage.setSegmentMembership(randomUser_1.getEmail(), "ACQ_CustomerVIP_MAILABLE");

		// Update database, add user to the segment
		CustomLogger.log("Going to update DB...");
		DBUtils.updateSegmentsProperty(randomUser_1.getEmail(), "AF");

		// login and perform search

         loginToSearch(randomUser_1);
         homePage.closeOptinLigthBox();
         Assert.assertTrue(searchResultsPage.isHighRiskVideoExists("PCH1"),"High risk video does not display");
         homePage.closeUserLevelLightBox();			
		//validate video should not display at the SERP

		loginToSearch(randomUser_1);
		homePage.closeUserLevelLightBox();
		// validate video should not display at the SERP
		Assert.assertTrue(searchResultsPage.isHighRiskVideoExists("PCH1"), "High risk video does not display");

	}

	@Test(testName = "TestID=21279", description = "Add user to the same segment as the video and validate user is able to watch it", dependsOnMethods = "test_HighRiskVideoForSegmentUser_FirstTime")
	public void test_HighRiskVideoForSegmentUser_SecondTime() throws SQLException {

		loginToSearch(randomUser_1);
		// validate video should not display at the SERP
		Assert.assertFalse(searchResultsPage.isHighRiskVideoExists("PCH"), "High risk video does not display");
		// update database remove entries for the video
		DBUtils.updateTokensForWatchingVideos(randomUser_1.getEmail(), "videoplayer_37");

		//validate video should display at the SERP
		homePage.refreshPage();
		loginToSearch(randomUser_1);	
		Assert.assertTrue(searchResultsPage.isHighRiskVideoExists("PCH1"),"High risk video does not display");

		// validate video should display at the SERP
		Assert.assertTrue(searchResultsPage.isHighRiskVideoExists("PCH1"), "High risk video does not display");
	}
}
