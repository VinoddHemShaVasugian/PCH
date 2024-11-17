package com.pch.sw.tests.iwe;

import java.sql.SQLException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.pch.search.iwe.AllPlaysPage;
import com.pch.search.iwe.DevicePage;
import com.pch.search.iwe.GiveawayPage;
import com.pch.search.iwe.IWEBasePage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.WinnersLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class ReconfigureIWEDevicesTest extends BaseTest {

	IWEBasePage iweBasePage; // new IWEBasePage();
	DevicePage devicePage;
	GiveawayPage giveawayPage;
	AllPlaysPage allPlaysPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	WinnersLightBox winnersLightBox;
	CentralServicesPage centralServicesPage;
	AdminBasePage joomlaPage;
	User randomUser_1, randomUser_2, randomUser_3, randomUser_4;

	String winnerMessageForNoSegmentUser = "You have won $10 Cash!";
	String winnerMessageForSegmentUser = "You have won $20 Cash!";

	String device_96 = "SearchAndWin - Site -  First Search - no segment";
	String device_1506 = "SearchAndWin - Site - Search2-5 - no segment";
	String device_926 = "SearchAndWin - Site - Search6+ - no segment";

	String device_1501 = "SearchAndWin - Site - First Search - Supersearchers Segment";
	String device_1511 = "SearchAndWin - Site - Search2-5 - Supersearchers Segment";
	String device_1468 = "SearchAndWin - Site - Search6+ - Supersearchers Segment";

	String segmentUser = "CUSTOMER_VIP";
	String first_search_token_activity_message = "For Your First Search of the Day!";
	String later_first_search_token_activity_message = "For Searching On Search&Win";

	@Test
	public void testConfigureSegmentUserInJoomla() {

		joomlaPage.goToArticle("Prize Machine / Instant Win / Segmentation");
		// get selected segment
		String Selectedsegment = joomlaPage.getSelectedSegment("pch_iwe_instantwin_device_group_0_segments_included");
		if (!Selectedsegment.equals(segmentUser)) {
			joomlaPage.selectSegmentByText("pch_iwe_instantwin_device_group_0_segments_included", segmentUser);
		}
		joomlaPage.saveCloseAndClearCache();
	}

	@Test
	public void testCashPrizeForNonSegmentUser() {
		try {
			// Configure the IWE Device to get Cash prize
			iweBasePage.logIn();
			devicePage.makeGiveawayActive("96");
			// Login to the PCH to get Cash prize
			loginToSearch(randomUser_1);
			homePage.closeUserLevelLightBox();
			headerPage.search("PCH", false);
			Common.sleepFor(2000);
			String win_message = homePage.getMessageFromWinnerLightBox();
			CustomLogger.log("Win Lightbox Message: " + win_message);
			Assert.assertEquals(win_message, winnerMessageForNoSegmentUser,
					"Cash prize win message for Non Segmented User is displayed different.");
			homePage.closeWinnerLightBox();
			iweBasePage.logIn();
			Common.sleepFor(3000);
			iweBasePage.navigateToWinnerListPage();
			Assert.assertTrue(iweBasePage.getWinnerEmailDetails(randomUser_1.getEmail()));
			// make cash device inactive
			devicePage.makeGiveawayInactive("96");
		} catch (Exception ex) {
			iweBasePage.logIn();
			Common.sleepFor(3000);
			CustomLogger.log(ex.getMessage());
			devicePage.makeGiveawayInactive("96");
			throw ex;
		}
	}

	@Test(dependsOnMethods = "testConfigureSegmentUserInJoomla")
	public void testCashPrizeForSegmentUser() throws SQLException {
		try {
			// Configure the IWE Device to get Cash prize
			iweBasePage.logIn();
			devicePage.makeGiveawayActive("1501");
			// Add user into the segment
			centralServicesPage.createFullRegAndGetGMT(randomUser_2);
			centralServicesPage.navigateToSegmentMembershipPage();
			centralServicesPage.setSegmentMembership(randomUser_2.getEmail(), segmentUser);

			// Update database, add user to the segment
			CustomLogger.log("Going to update DB...");
			DBUtils.updateSegmentsProperty(randomUser_2.getEmail(), "CUSTV");
			// Login to PCH to get Cash prize
			loginToSearch(randomUser_2);
			homePage.closeUserLevelLightBox();
			headerPage.search("PCH", false);
			Common.sleepFor(2000);
			String win_message = homePage.getMessageFromWinnerLightBox();
			CustomLogger.log("Win Lightbox Message: " + win_message);
			Assert.assertEquals(win_message, winnerMessageForSegmentUser,
					"Cash prize win message for Segmented User is displayed different.");
			homePage.closeWinnerLightBox();
			iweBasePage.logIn();
			Common.sleepFor(3000);
			iweBasePage.navigateToWinnerListPage();
			Assert.assertTrue(iweBasePage.getWinnerEmailDetails(randomUser_2.getEmail()));
			// make cash device inactive
			devicePage.makeGiveawayInactive("1501");
		} catch (Exception ex) {
			iweBasePage.logIn();
			Common.sleepFor(3000);
			CustomLogger.log(ex.getMessage());
			devicePage.makeGiveawayInactive("1501");
			throw ex;
		}
	}

//	@Test
	public void testTokenValueForNonSegmentUser() {

		// To get the current First Search Token amount
		joomlaPage.goToArticle("Prize Machine / Tokens / First Search");
		int desktop_first_search_token_value = Integer.parseInt(joomlaPage.getDesktopFirstSearchTokenValue());
		// To get the assigned Token CatchAll values from the IWE Admin. We
		// presume the device will contain only one Token CatchAll
		// configuration.
		iweBasePage.logIn();
		Common.sleepFor(3000);
		int non_segement_prize_value_for_2to5 = Integer.parseInt(devicePage.getTokenCatchAllPrizeValue("1506"));
		int non_segement_prize_value_for_6to25 = Integer.parseInt(devicePage.getTokenCatchAllPrizeValue("926"));

		loginToSearch(randomUser_3);
		homePage.closeUserLevelLightBox();
		for (int i = 1; i < 26; i++) {
			headerPage.search("PCH" + i);
			List<String> activityNames = headerPage.getLastActivityBotResult().getActivityNames();
			System.out.println("verifying tokens for: " + i + " search");

			// validate tokens for first search
			if (i == 1) {
				Assert.assertTrue(activityNames.contains(first_search_token_activity_message),
						"Token Activity message doesn't contain the First search message. Actual message is-"
								+ activityNames + " -and expected is-" + first_search_token_activity_message);
				int index = activityNames.indexOf(first_search_token_activity_message);
				int actualTokensReceivedOnAnniversary = headerPage.getLastActivityBotResult()
						.getTokensEarnedInActivity(index);
				Assert.assertEquals(actualTokensReceivedOnAnniversary, desktop_first_search_token_value);
			}
			// validate tokens for 2-5 searches
			if (i > 1 && i < 6) {
				Assert.assertTrue(activityNames.contains(later_first_search_token_activity_message),
						"Token Activity message doesn't contain the later First search message. Actual message is-"
								+ activityNames + " -and expected is-" + later_first_search_token_activity_message);
				int index = activityNames.indexOf(later_first_search_token_activity_message);
				int actualTokensReceivedOnAnniversary = headerPage.getLastActivityBotResult()
						.getTokensEarnedInActivity(index);
				Assert.assertEquals(actualTokensReceivedOnAnniversary, non_segement_prize_value_for_2to5);
			}
			// validate tokens for 6+ searches
			if (i > 5 && i < 26) {
				Assert.assertTrue(activityNames.contains(later_first_search_token_activity_message),
						"Token Activity message doesn't contain the later First search message. Actual message is-"
								+ activityNames + " -and expected is-" + later_first_search_token_activity_message);
				int index = activityNames.indexOf(later_first_search_token_activity_message);
				int actualTokensReceivedOnAnniversary = headerPage.getLastActivityBotResult()
						.getTokensEarnedInActivity(index);
				Assert.assertEquals(actualTokensReceivedOnAnniversary, non_segement_prize_value_for_6to25);
			}
		}
		// Validate the Token Credit calls
		iweBasePage.logIn();
		Common.sleepFor(4000);
		iweBasePage.navigateToUserInfoModal(randomUser_3.getEmail());
		devicePage.clickTokenCatchAllWinsTab();
		Common.sleepFor(2000);
		Assert.assertEquals(allPlaysPage.getDeviceCount(device_1506), 4);
		Assert.assertEquals(allPlaysPage.getDeviceCount(device_926), 16);
		devicePage.nextPagePaginationByArrow();
		Common.sleepFor(1000);
		Assert.assertEquals(allPlaysPage.getDeviceCount(device_926), 4);
	}

//	@Test(dependsOnMethods = "testConfigureSegmentUserInJoomla")
	public void testTokenValueForSegmentUser() throws SQLException {

		// To get the current First Search Token amount
		joomlaPage.goToArticle("Prize Machine / Tokens / First Search");
		int desktop_first_search_token_value = Integer.parseInt(joomlaPage.getDesktopFirstSearchTokenValue());
		// To get the assigned Token CatchAll values from the IWE Admin. We
		// presume the device will contain only one Token CatchAll
		// configuration.
		iweBasePage.logIn();
		Common.sleepFor(3000);
		int segement_prize_value_for_2to5 = Integer.parseInt(devicePage.getTokenCatchAllPrizeValue("1511"));
		int segement_prize_value_for_6to25 = Integer.parseInt(devicePage.getTokenCatchAllPrizeValue("1468"));

		loginToSearch(randomUser_4);
		homePage.closeUserLevelLightBox();
		// add user to the segment
		centralServicesPage.navigateToSegmentMembershipPage();
		centralServicesPage.setSegmentMembership(randomUser_4.getEmail(), segmentUser);

		// Update database, add user to the segment
		CustomLogger.log("Going to update DB...");
		DBUtils.updateSegmentsProperty(randomUser_4.getEmail(), "CUSTV");

		// login and validate tokens
		loginToSearch(randomUser_4);
		homePage.closeUserLevelLightBox();
		for (int i = 1; i < 26; i++) {
			headerPage.search("PCH" + i);
			List<String> activityNames = headerPage.getLastActivityBotResult().getActivityNames();
			System.out.println("verifying tokens for: " + i + " search");

			// validate tokens for first search
			if (i == 1) {
				Assert.assertTrue(activityNames.contains(first_search_token_activity_message),
						"Token Activity message doesn't contain the First search message. Actual message is-"
								+ activityNames + " -and expected is-" + first_search_token_activity_message);
				int index = activityNames.indexOf(first_search_token_activity_message);
				int actualTokensReceivedOnAnniversary = headerPage.getLastActivityBotResult()
						.getTokensEarnedInActivity(index);
				Assert.assertEquals(actualTokensReceivedOnAnniversary, desktop_first_search_token_value);
			}
			// validate tokens for 2-5 searches
			if (i > 1 && i < 6) {
				Assert.assertTrue(activityNames.contains(later_first_search_token_activity_message),
						"Token Activity message doesn't contain the later First search message. Actual message is-"
								+ activityNames + " -and expected is-" + later_first_search_token_activity_message);
				int index = activityNames.indexOf(later_first_search_token_activity_message);
				int actualTokensReceivedOnAnniversary = headerPage.getLastActivityBotResult()
						.getTokensEarnedInActivity(index);
				Assert.assertEquals(actualTokensReceivedOnAnniversary, segement_prize_value_for_2to5);
			}
			// validate tokens for 6+ searches
			if (i > 5 && i < 26) {
				Assert.assertTrue(activityNames.contains(later_first_search_token_activity_message),
						"Token Activity message doesn't contain the later First search message. Actual message is-"
								+ activityNames + " -and expected is-" + later_first_search_token_activity_message);
				int index = activityNames.indexOf(later_first_search_token_activity_message);
				int actualTokensReceivedOnAnniversary = headerPage.getLastActivityBotResult()
						.getTokensEarnedInActivity(index);
				Assert.assertEquals(actualTokensReceivedOnAnniversary, segement_prize_value_for_6to25);
			}
		}
		// Validate the Token Credit calls
		iweBasePage.logIn();
		Common.sleepFor(4000);
		iweBasePage.navigateToUserInfoModal(randomUser_4.getEmail());
		devicePage.clickTokenCatchAllWinsTab();
		Common.sleepFor(2000);
		Assert.assertEquals(allPlaysPage.getDeviceCount(device_1511), 4);
		Assert.assertEquals(allPlaysPage.getDeviceCount(device_1468), 16);
		devicePage.nextPagePaginationByArrow();
		Common.sleepFor(1000);
		Assert.assertEquals(allPlaysPage.getDeviceCount(device_1468), 4);
	}
}
