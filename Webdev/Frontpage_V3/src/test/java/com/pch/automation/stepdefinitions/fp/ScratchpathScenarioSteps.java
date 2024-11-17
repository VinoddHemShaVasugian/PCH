package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.steps.ArticlesSteps;
import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.SerpSteps;
import com.pch.automation.steps.fp.HoroscopeSteps;
import com.pch.automation.steps.fp.LotterySteps;
import com.pch.automation.steps.fp.ScratchpathSteps;
import com.pch.automation.steps.fp.WeatherSteps;

import net.thucydides.core.annotations.Steps;

public class ScratchpathScenarioSteps {

	@Steps
	ScratchpathSteps scratchcardSteps;
	@Steps
	NavigationSteps navSteps;
	@Steps
	LotterySteps lotterySteps;
	@Steps
	HoroscopeSteps horoscopeSteps;
	@Steps
	SerpSteps serpSteps;
	@Steps
	WeatherSteps weatherSteps;
	@Steps
	ArticlesSteps articleSteps;
	@Steps
	HomepageSteps homePageSteps;

	/**
	 * Verify the Scratchcard page redirection by its title
	 * 
	 */
	@Then("Verify the application redirected to Scratchcard page")

	public void verifyScratchpathPage() {
		assertEquals("Failed to redirect to Scratchcard page", "Scratchpath", scratchcardSteps.pageTitle());
	}

	/**
	 * Verify the presence of the Scratchcard light box
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the presence of the Scratchcard light box")
	public void verifyAbsenceOfScratchcardLB() {
		assertTrue("Failed to display to Scratchcard light box", scratchcardSteps.verifyScratchcardLB());
		scratchcardSteps.closeScratchcardLB();
	}

	@Then("Verify the application redirected to homepage instead of scratchcard page")
	public void verifyHomePage() throws SQLException, IOException {
		assertTrue("Failed to redirect to homepage after completed the scratchpath games.",
				scratchcardSteps.verifyHome());
		assertEquals("Failed to redirect to Homepage after completed the scratchpath games.", "Frontpage",
				scratchcardSteps.pageTitle());
	}

	@Then("Play and verify scratchpath games")
	public void playScratchpathGames() throws Exception {
		String scratchPathToken;
		int totalGameCount = scratchcardSteps.getScratchPathTotalGameCount();
		for (int count = 1; count <= totalGameCount; count++) {
			if (scratchcardSteps.verifyBGIcon()) {
				scratchcardSteps.clickPlayBGicon();
			}
			scratchcardSteps.waitForScratchPathAdsToComplete();
			scratchPathToken = scratchcardSteps.playScratchPathGame();
			scratchcardSteps.waitForScratchPathAdsToComplete();
			navSteps.redirectToTokenHistoryPage();
			if (count == totalGameCount) {
				assertEquals("Awarded token amount mismatch.", scratchcardSteps.getTokenTransactionAmount(2),
						scratchPathToken);
			} else {
				assertEquals("Awarded token amount mismatch.", scratchcardSteps.getTokenTransactionAmount(1),
						scratchPathToken);
			}
			navSteps.navigateToFPApplication();
		}
	}

	@When("Verify the daily bonus game info window")
	public void verifyBonusGameInfoWindow() {
		scratchcardSteps.clickBGInfoWindow();
		assertTrue("Failed to display bonus game info window.", scratchcardSteps.verifyBGInfoWindow());
		scratchcardSteps.closeBGInfoWindow();
		assertFalse("Bonus game info window should disappear.", scratchcardSteps.verifyBGInfoWindow());
	}

	@Then("Do a search and verify daily bonus game progress bar")
	public void searchAndVerifyProgressBar() {
		serpSteps.searchAndVerifySERPPage();
		navSteps.navigateToFPApplication();
		assertEquals("Daily bonus game level 1 is not completed.", 1,
				scratchcardSteps.getDailyBonusGameProgressLevel());
	}

	@Then("Read articles and verify daily bonus game progress bar")
	public void readArticlesAndVerifyProgressBar() {
		articleSteps.navigateToArticlePage();
		homePageSteps.clickClaimButton();
		navSteps.navigateToFPApplication();
		assertEquals("Daily bonus game level 2 is not completed.", 2,
				scratchcardSteps.getDailyBonusGameProgressLevel());
	}

	@Then("Claim Horoscope tokens and verify daily bonus game progress bar")
	public void claimHoroscopeTokensAndVerifyProgressBar() {
		horoscopeSteps.clickHoroscopeMenu();
		horoscopeSteps.claimTokens();
		navSteps.navigateToFPApplication();
		assertEquals("Daily bonus game level 3 is not completed.", 3,
				scratchcardSteps.getDailyBonusGameProgressLevel());
	}

	@Then("Claim Lottery tokens and verify daily bonus game progress bar")
	public void claimLotteryTokensAndVerifyProgressBar() {
		homePageSteps.clickLotteryMenu();
		lotterySteps.claimTokens();
		navSteps.navigateToFPApplication();
		assertEquals("Daily bonus game level 4 is not completed.", 4,
				scratchcardSteps.getDailyBonusGameProgressLevel());
	}

	@Then("Claim Weather tokens and verify daily bonus game progress bar")
	public void claimWeatherTokensAndVerifyProgressBar() {
		weatherSteps.clickWeatherMenu();
		weatherSteps.claimTokens();
		navSteps.navigateToFPApplication();
		assertEquals("Daily bonus game level 5 is not completed.", 5,
				scratchcardSteps.getDailyBonusGameProgressLevel());
	}

	@Then("Verify the presence of play bonus game icon")
	public void verifyPresenceOfBonusGameIcon() {
		assertTrue("Bonus game icon is not enabled.", scratchcardSteps.verifyBGIcon());
	}

	@Then("Verify the absence of play bonus game icon")
	public void verifyAbsenceOfBonusGameIcon() {
		assertFalse("Bonus game icon is not enabled.", scratchcardSteps.verifyBGIcon());
	}

	@Then("Verify the play bonus game property on database '$staus'")
	public void verifyBonusGamePropertyOnDB(String status) throws NumberFormatException, IOException, SQLException {
		assertEquals("Bonus game property is not updated in DB.", Integer.parseInt(status),
				scratchcardSteps.getDailyProgressMissionDetails());
	}

	@When("Navigate to scratchcard page")
	public void navigateToScratchcardPage() {
		scratchcardSteps.clickPlayBGicon();
	}
}