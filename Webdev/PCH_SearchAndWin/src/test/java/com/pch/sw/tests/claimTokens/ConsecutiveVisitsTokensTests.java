package com.pch.sw.tests.claimTokens;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.bean.JoomlaProperty;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.MyAccountPage;
import com.pch.search.pages.web.MyAccountPage.MyAccountTab;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class ConsecutiveVisitsTokensTests extends BaseTest {

	// Test case location in QC pch_searchNWin>Desktop>Q1 S18 1/20-2/9

	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	MyAccountPage myAccountPage;
	TokenHistoryPage tokenHistoryPage;
	JoomlaProperty joomlaProperty;
	User randomUser_1;
	int tokensForFirstDayVisit = 5000;
	int tokensForSecondDayVisit = 10000;
	int tokensForThirdDayVisit = 15000;
	int tokensForFourthDayVisit = 20000;
	int tokensForFifthDayVisit = 25000;
	String dateFormat = "yyyy-MM-dd";
	String timeZone = "America/New_York";
	String currentDate = Common.getCurrentDate(timeZone, dateFormat);
	String message = "You've just received your daily entry to win a prize. Keep searching for chances to win today's instant prizes!";
	List<String> propertiesToRead;

	@Test(enabled=true, description = "configure 'Prize Machine / Tokens / Consecutive Visit [DT]' article in joomla", priority = 3)
	public void testFiveDayMissionTokenConfiguration() {

		// configure article in joomla
		joomlaPage
				.goToArticle("Prize Machine / Tokens / Consecutive Visit [DT]");

		propertiesToRead = new ArrayList<String>();
		propertiesToRead.add(String.valueOf(tokensForFirstDayVisit));
		propertiesToRead.add(String.valueOf(tokensForSecondDayVisit));
		propertiesToRead.add(String.valueOf(tokensForThirdDayVisit));
		propertiesToRead.add(String.valueOf(tokensForFourthDayVisit));
		propertiesToRead.add(String.valueOf(tokensForFifthDayVisit));
		Assert.assertEquals(
				joomlaPage.setProperties("Token Amount", propertiesToRead),
				"success");
	}

	// ,dependsOnMethods="testFiveDayMissionTokenConfiguration"
	@Test(description = "verify user gets awarded for consecutive visits", dependsOnMethods = "testFiveDayMissionTokenConfiguration")
	public void ConsecutiveVisit_Day1_To_Day6() throws SQLException,
			InterruptedException {

		List<String> propertiesToRead1 = new ArrayList<String>();
		propertiesToRead1.add("For your first consecutive visit");
		propertiesToRead1.add("For your second consecutive visit");
		propertiesToRead1.add("For your third consecutive visit");
		propertiesToRead1.add("For your fourth consecutive visit");
		propertiesToRead1.add("For your fifth consecutive visit");

		loginToSearch(randomUser_1);
		//homePage.closeUserLevelLightBox();

		for (int i = 1; i < 7; i++) {
			headerPage.search("shoes", false);

			// validate consecutive visit progress bar displays
			Assert.assertTrue(homePage.isConsecutiveVisitDisplay(),
					"Consecutive visit does not display");

			if (i == 5 || i == 6) {
				// for the 5th and 6th consecutive visit
				Assert.assertTrue(
						homePage.isConsecutiveVisitTokensAwardedForFifthtDay(),
						"Consecutive visit token for day:" + i + " not awarded");
			} else {
				Assert.assertTrue(homePage
						.isConsecutiveVisitTokensAwardedForCurrentDay(i),
						"Consecutive visit token for day:" + i + " not awarded");
			}

			// Assert.assertEquals(homePage.getMessage(),message,"Meassge: "+message+" does not display");

			if (i < 6) {
				// Update database, Delete first search,daily search count &
				// consecutive visits to yesterday's day
				// in sso-data & sso-properties_grouped

				// update DB for 2nd, 3rd...6th visit
				CustomLogger.log("Going to update DB...");

				// update daily search count
				DBUtils.updateDailySearchCount(randomUser_1.getEmail(), 0);

				// update first search count
				DBUtils.updateFirstSearchCount(randomUser_1.getEmail(),
						"{\"D\":\"0,\"T\":0,\"M\":0}");

				// update ConsecutiveVisits
				String visitDate = Common.addDaysToDate(currentDate,
						dateFormat, -1);
				String updatedVisitDate = "a:2:{s:14:\"lastSearchDate\";"
						+ "s:10:\"" + visitDate + "\";" + "s:6:\"visits\";"
						+ "i:" + i + ";}";
				DBUtils.updateLastVisitDate(randomUser_1.getEmail(),
						updatedVisitDate);
				
				if (Environment.getDevice().equalsIgnoreCase("mobile")) {
					homePage.goToMyAccountPage();
					myAccountPage.navigateToTab(MyAccountTab.Token_History);
					Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
							String.valueOf(propertiesToRead1.get(i-1))));
					
					homePage.load();
					if(i==1){
						homePage.closeUserLevelLightBox();
					}
				}
			}

		}
		
		// Navigate to MyAccount Page page and validate tokens for this visit
		if (!Environment.getDevice().equalsIgnoreCase("mobile")) {
			//Navigate to MyAccount Page page and validate tokens for this visit
			homePage.goToMyAccountPage();
			myAccountPage.navigateToTab(MyAccountTab.Token_History);				
								
			for(int i=0;i<propertiesToRead1.size();i++){
			Assert.assertEquals(tokenHistoryPage.getTokenForEvent(propertiesToRead1.get(i)),String.valueOf(propertiesToRead.get(i)));
			}
		}
	}

	@Test(enabled=false, description = "Verify no consecutive visit tokens got awarded if article 'PCH Search - Consecutive Visits' is unpublih in joomla")
	public void ConsecutiveVisitTokensNotAwardedForUnpublishedArticle()
			throws SQLException {
		// Unpublish module 'PCH Search - Consecutive Visits' in joomla
		joomlaPage.unpublishModule("PCH Search - Consecutive Visits", null);

		loginToSearch(randomUser_1);
		headerPage.search("PCH", false);
		homePage.closeUserLevelLightBox();
		Assert.assertFalse(homePage.isConsecutiveVisitDisplay(),
				"Consecutive visit displays");
		// publish module 'PCH Search - Consecutive Visits' in joomla
		joomlaPage.publishModule("PCH Search - Consecutive Visits", null);
	}

	@Test(description = "Verify no consecutive visit tokens got awarded if user missed a day to visit the search page", dependsOnMethods = "ConsecutiveVisit_Day1_To_Day6")
	public void ConsecutiveVisitTokensNotAwardedForSkipDay()
			throws SQLException, InterruptedException {
		int day = 2;

		// Update database, Delete first search,daily search count & consecutive
		// visits to yesterday's day
		// in sso-data & sso-properties_grouped

		CustomLogger.log("Going to update DB...");
		// update daily search count
		DBUtils.updateDailySearchCount(randomUser_1.getEmail(), 0);

		// update first search count
		DBUtils.updateFirstSearchCount(randomUser_1.getEmail(),
				"{\"D\":\"0,\"T\":0,\"M\":0}");

		// update ConsecutiveVisits date to 2 days back
		String visitDate = Common.addDaysToDate(currentDate, dateFormat, -2);
		String updatedVisitDate = "a:2:{s:14:\"lastSearchDate\";" + "s:10:\""
				+ visitDate + "\";" + "s:6:\"visits\";" + "i:1;}";
		DBUtils.updateLastVisitDate(randomUser_1.getEmail(), updatedVisitDate);

		loginToSearch(randomUser_1);
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH", false);
		
		if (Environment.getDevice().equalsIgnoreCase("mobile")) {
			homePage.goToMyAccountPage();
			myAccountPage.navigateToTab(MyAccountTab.Token_History);
			Assert.assertTrue(tokenHistoryPage.getActivityName().contains("For your first consecutive visit"));
			
		}else{
			// validate consecutive visit progress bar displays
			Assert.assertTrue(homePage.isConsecutiveVisitDisplay(),
					"Consecutive visit does not display");
			// validate consecutive visit token does not awarded for this visit
			Assert.assertFalse(
					homePage.isConsecutiveVisitTokensAwardedForCurrentDay(day),
					"Consecutive visit token for day:" + day + " awarded");
		}

		
	}
	@Test(description="consecutive visit for first search alone")
	public void ConsecutiveVisit_Day1() throws InterruptedException, SQLException{
	loginToSearch(randomUser_1);
	for(int i=1;i<=1;i++){
		headerPage.search("shoes");
		if(i==1){
			homePage.goToTokenHistory();
			Assert.assertFalse(tokenHistoryPage.getActivityName().contains(" For your first search of the day "));
			Assert.assertFalse(tokenHistoryPage.getActivityName().contains("first consecutive tokens"));
		}
      }
	}
}