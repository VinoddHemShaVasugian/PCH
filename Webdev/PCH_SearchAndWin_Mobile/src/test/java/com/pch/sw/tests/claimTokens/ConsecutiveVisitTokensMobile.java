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
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class ConsecutiveVisitTokensMobile extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	MyAccountPage myAccountPage;
	TokenHistoryPage tokenHistoryPage;
	JoomlaProperty joomlaProperty;
	User randomUser_1, randomUser_2, randomUser_3;
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
	String first_consecutive_visit_article = "For Your First Consecutive Visit";
	String second_consecutive_visit_article = "For Your Second Consecutive Visit";
	String third_consecutive_visit_article = "For Your Third Consecutive Visit";
	String fourth_consecutive_visit_article = "For Your Fourth Consecutive Visit";
	String fifth_consecutive_visit_article = "For Your Fifth Consecutive Visit";

	@Test(description = "configure 'Prize Machine / Tokens / Consecutive Visit [DT]' article in joomla")
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

	@Test(dependsOnMethods = "testFiveDayMissionTokenConfiguration", description = "User should award tokens for five day mission",priority=1)
	public void ConsecutiveVisit_Day1_To_Day6() throws InterruptedException,
			SQLException {
		loginToSearch(randomUser_1);
		for (int i = 1; i <= 6; i++) {
			headerPage.search("shoes");
			if (i == 1) {
				homePage.goToTokenHistory();
	       System.out.println(tokenHistoryPage.getActivityName());
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(first_consecutive_visit_article));
			}
			if (i == 2) {
				homePage.goToTokenHistory();
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
						second_consecutive_visit_article));
			}
			if (i == 3) {
				homePage.goToTokenHistory();
				Common.sleepFor(2000);
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
						third_consecutive_visit_article));
			}
			if (i == 4) {
				homePage.goToTokenHistory();
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
						fourth_consecutive_visit_article));
			}
			if (i == 5) {
				homePage.goToTokenHistory();
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
						fifth_consecutive_visit_article));
			}
			// update DB for 2nd, 3rd...6th visit
			CustomLogger.log("Going to update DB...");

			// update daily search count
			DBUtils.updateDailySearchCount(randomUser_1.getEmail(), 0);

			// update first search count
			DBUtils.updateFirstSearchCount(randomUser_1.getEmail(),
					"{\"D\":\"0,\"T\":0,\"M\":0}");

			// update ConsecutiveVisits
			String visitDate = Common
					.addDaysToDate(currentDate, dateFormat, -1);
			String updatedVisitDate = "a:2:{s:14:\"lastSearchDate\";"
					+ "s:10:\"" + visitDate + "\";" + "s:6:\"visits\";" + "i:"
					+ i + ";}";
			DBUtils.updateLastVisitDate(randomUser_1.getEmail(),
					updatedVisitDate);
			homePage.load();
			homePage.closeUserLevelLightBox();
			homePage.refreshPage();
		}

	}

	@Test(dependsOnMethods = "testFiveDayMissionTokenConfiguration", description = "the user should see first consecutive tokens for skipping a day",priority=2)
	public void SkipConsecutiveday() throws InterruptedException, SQLException {
		loginToSearch(randomUser_2);
		for (int i = 1; i <= 2; i++) {
			headerPage.search("shoes");
			if (i == 1) {
				homePage.goToTokenHistory();
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
						first_consecutive_visit_article));
			}
			if (i == 2) {
				homePage.goToTokenHistory();
				Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
						first_consecutive_visit_article));
			}
			CustomLogger.log("Going to update DB...");

			// update daily search count
			DBUtils.updateDailySearchCount(randomUser_2.getEmail(), 0);

			// update first search count
			DBUtils.updateFirstSearchCount(randomUser_2.getEmail(),
					"{\"D\":\"0,\"T\":0,\"M\":0}");

			// update ConsecutiveVisits
			String visitDate = Common
					.addDaysToDate(currentDate, dateFormat, -2);
			String updatedVisitDate = "a:2:{s:14:\"lastSearchDate\";"
					+ "s:10:\"" + visitDate + "\";" + "s:6:\"visits\";" + "i:"
					+ i + ";}";
			DBUtils.updateLastVisitDate(randomUser_2.getEmail(),
					updatedVisitDate);
			homePage.load();
			homePage.closeUserLevelLightBox();
			homePage.refreshPage();
		}
	}

	@Test(description = "consecutive visit for first search alone",priority=3)
	public void ConsecutiveVisit_Day1() throws InterruptedException,
			SQLException {
		loginToSearch(randomUser_1);
		headerPage.search("shoes");
		Common.sleepFor(2000);
		homePage.goToTokenHistory();
		System.out.println("expected message:"
				+ tokenHistoryPage.getActivityName());
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
				"For Your First Search of the Day.!"));
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(
				first_consecutive_visit_article));
	}
}
