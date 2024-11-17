package com.pch.sw.tests.misc;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class VideoForSpecificUsersTests extends BaseTest {

	private AdminBasePage joomlaPage;
	private HomePage homePage;
	private WebHeaderPage webHeaderPage;
	private CentralServicesPage centralServicesPage;
	User randomUser, registeredUser;

	/*
	 * Scenario : validate video pop-up on search home page By adding user to
	 * segment and include segment in joomla, login with the user and check for
	 * video pop up sign-out and sign again should not see the video pop-up
	 * Reset DB and login again should see the video pop-up.
	 */
	@Test(description = "validting video pop-up on search home page for segmented user - 21249")
	public void videoPopupSegmentedUser() throws SQLException {

		/*
		 * Scenario: 1 - Create a user and do segmentation Check for video pop
		 * up Sign-out and sign in with the same user, you should not see the
		 * video pop upbt Clear DB and login again
		 */

		// include ACQ_TABLET_INACTIVE segment in joomla Article "Video - SERP -
		// High Risk"
		joomlaPage.goToArticle("Video - SERP - High Risk");
		CustomLogger.log("Selecting ACQ_TABLET_INACTIVE segment in joomla Article ");
		// get selected segment
		String Selectedsegment = joomlaPage.getSelectedSegment("pch_video_player_group_0_segments_included");
		// select segment "ACTIVE_FP_L60" if nothing is selected
		if (!Selectedsegment.equals("ACQ_TABLET_INACTIVE")) {
			joomlaPage.selectSegmentByText("pch_video_player_group_0_segments_included", "ACQ_TABLET_INACTIVE");
		}
		joomlaPage.saveCloseAndClearCache();

		// Create a full reg user and get GMT
		CustomLogger.log("Creating Full reg user in Reg foundation page and get GMT");

		String gmtOfUSer = centralServicesPage.createFullRegAndGetGMT(randomUser);

		// ad user into segment
		CustomLogger.log("adding segmentation to the User - segment code : ACQ_TABLET_INACTIVE");
		centralServicesPage.navigateToSegmentMembershipPage();
		centralServicesPage.setSegmentMembership(randomUser.getEmail(), "ACQ_TABLET_INACTIVE");

		// add userEmail, GMT and segid=video, and navigate to the link
		CustomLogger.log("Add userEmail, GMT, segid=video and load the url ");
		String videoPopUpURL = "http://search." + Environment.getEnvironment()
				+ ".pch.com/?segid=video&src1=swemail&src2=13S2505&mailid=pch13S2505&e=" + gmtOfUSer
				+ "&edid=HCO2IX-7DG2I-HJZBRU-T42QPH-4TDT5-v1&tc=204SX4880K&v=20073489&email=" + randomUser.getEmail()
				+ "&fn=" + randomUser.getFirstname() + "&ln=" + randomUser.getLastname()
				+ "&ci=Port%20Washington&st=NY";
		homePage.load(videoPopUpURL);

		// close optin and validate video pop-up
		CustomLogger.log("Validating the video pop up for the first time..");
		Assert.assertTrue(homePage.videoPopUP().isDisplayed());

		// When we visit the same page again we should not see the video pop up
		CustomLogger
				.log("Revisiting the site again to validate video pop-up, expected - should not see the video pop-up");
		homePage.load(videoPopUpURL);
		homePage.closeUserLevelLightBox();
		Assert.assertFalse(homePage.videoPopUpCount() > 0);
		webHeaderPage.signOut();
		CustomLogger.log("User " + randomUser.getEmail());
		CustomLogger.log("Didin't find video pop up on revisiting the URL for the second time");

		// Reset DB of the user so that we should see the video pop-up again
		CustomLogger.log("Reset DB and check video pop-up");
		DBUtils.updateVideoPlayerProperty(randomUser.getEmail(), "0");

		// Navigate to user and check for Video pop up
		CustomLogger.log("validating video pop-up after DB reset, expected  - should see video pop-up");
		Common.sleepFor(2000);
		homePage.load(videoPopUpURL);
		Assert.assertTrue(homePage.videoPopUP().isDisplayed());

	}

	@Test(description = "verifying video pop-up on search home page for non-segmented user - 21249")
	public void videoPopupForNonSegmentedUser() {

		homePage.load();
		webHeaderPage.loginToSearch(registeredUser, registeredUser.getPassword());
		CustomLogger.log("Verifying video pop-up for non-segmneted user.");
		Assert.assertFalse(homePage.videoPopUpCount() > 0);
		CustomLogger.log("Didn't find any video pop-up for the user who is not segmented");
	}

}
