package com.pch.sw.tests.misc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class VideoForSpecificUsersTests extends BaseTest {

	private AdminBasePage joomlaPage;
	private HomePage homePage;
	RegistrationPage regPage;
	private WebHeaderPage webHeaderPage;
	private CentralServicesPage centralServicesPage;
	User randomUser, registeredUser;

	/*
	 * Scenario : validate video pop-up on search home page By adding user to
	 * segment and include segment in joomla, login with the user and check for
	 * video pop up sign-out and sign again should not see the video pop-up
	 * Reset DB and login again should see the video pop-up.
	 */
//	@Test(description = "validting video pop-up on search home page for segmented user - 21249")
	public void videoPopupSegmentedUser() throws SQLException {

		/*
		 * Scenario: 1 - Create a user and do segmentation Check for video pop
		 * up Sign-out and sign in with the same user, you should not see the
		 * video pop upbt Clear DB and login again
		 */
		// creating a list with segment code
		List<String> segmentCode = new ArrayList<String>();
		segmentCode.add("ATI");

		// include ACQ_TABLET_INACTIVE segment in joomla Article "Video - SERP -
		// High Risk"
		joomlaPage.goToArticle("Video - SERP - High Risk");
		CustomLogger.log("Selecting ACQ_TABLET_INACTIVE segment in joomla Article ");
		joomlaPage.selectSegmentByValue("ATI");
		joomlaPage.saveCloseAndClearCache();

		// Create a full reg user and get GMT
		CustomLogger.log("Creating Full reg user in Reg foundation page and get GMT");

		String gmtOfUSer = centralServicesPage.createFullRegAndGetGMT(randomUser);

		// adding user to segment - segmentation code : ATI
		CustomLogger.log("adding segmentation to the User - segment code : ATI");
		centralServicesPage.navigateToSegmentMembershipPage();
		centralServicesPage.setSegmentMembership(randomUser.getEmail(), "ACQ_TABLET_INACTIVE");

		// joomlaPage.setegmentMemberShip(randomUser.getEmail(), "code",
		// segmentCode);

		// add userEmail, GMT and segid=video, and navigate to the link
		CustomLogger.log("Add userEmail, GMT, segid=video and load the url ");
		String videoPopUpURL = "http://search." + Environment.getEnvironment()
				+ ".pch.com/?segid=video&src1=swemail&src2=13S2505&mailid=pch13S2505&e=" + gmtOfUSer
				+ "&edid=HCO2IX-7DG2I-HJZBRU-T42QPH-4TDT5-v1&tc=204SX4880K&v=20073489&email=" + randomUser.getEmail()
				+ "&fn=" + randomUser.getFirstname() + "&ln=" + randomUser.getLastname()
				+ "&ci=Port%20Washington&st=NY";
		CustomLogger.log("Video PopUp URL: " + videoPopUpURL);
		homePage.load(videoPopUpURL);

		// close optin and validate video pop-up
		CustomLogger.log("Validating the video pop up for the first time..");
		/* homePage.closeOptinLigthBox(); */
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
		homePage.load(videoPopUpURL);
		CustomLogger.log("validating video pop-up after DB reset, expected  - should see video pop-up");
		Assert.assertTrue(homePage.videoPopUP().isDisplayed());

	}

	@Test(description = "verifying video pop-up on search home page for non-segmented user - 21249")
	public void videoPopupForNonSegmentedUser() {

		homePage.load();
		loginToSearch(registeredUser);
		CustomLogger.log("Verifying video pop-up for non-segmneted user.");
		Assert.assertFalse(homePage.videoPopUpCount() > 0);
		CustomLogger.log("Didn't find any video pop-up for the user who is not segmented");
	}

}
