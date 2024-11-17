package com.pch.automation.stepdefinitions.fp;

import java.text.ParseException;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.fp.LotterySteps;

import net.thucydides.core.annotations.Steps;

public class LotteryScenarioSteps {
	@Steps
	LotterySteps lotterySteps;
	@Steps
	HomepageSteps homeSteps;

	@When("Verify the lottery tokens claimed status of the user and the Progres bar")
	public void verifyTokensClaimedStatus() {
		homeSteps.clickLotteryMenu();
		Assert.assertTrue("Token not claimed for the day", lotterySteps.claimTokens());
		Assert.assertEquals("Daily bonus game count is not equal.", 1, homeSteps.getDailyBonusGameCheckCount());
		Assert.assertTrue("Daily bonus game lock icon is not enabled.", homeSteps.verifyDailyBonusGameIconEnabled());
	}

	@When("Goto Lottery Past Result page")
	public void clickLotteryPastResult() {
		homeSteps.clickLotteryMenu();
		lotterySteps.clickPastResults();
	}

	@When("Goto Lottery Payout page")
	public void clickLotteryPayout() {
		homeSteps.clickLotteryMenu();
		lotterySteps.clickPayout();
	}

	@Then("Verify the Past Result page")
	public void verifyPastResultPage() {
		Assert.assertTrue("Failed to redirect to Lottery Past Result page", lotterySteps.verifyPastResults());
		Assert.assertTrue("Token not claimed for the day", lotterySteps.claimTokens());
		Assert.assertEquals("Daily bonus game count is not equal.", 1, homeSteps.getDailyBonusGameCheckCount());
		Assert.assertTrue("Daily bonus game lock icon is not enabled.", homeSteps.verifyDailyBonusGameIconEnabled());
	}

	@Then("Verify the Payout page")
	public void verifyPayoutPage() {
		Assert.assertTrue("Failed to redirect to Lottery Past Result page", lotterySteps.verifyPastResults());
		Assert.assertTrue("Token not claimed for the day", lotterySteps.claimTokens());
		Assert.assertEquals("Daily bonus game count is not equal.", 1, homeSteps.getDailyBonusGameCheckCount());
		Assert.assertTrue("Daily bonus game lock icon is not enabled.", homeSteps.verifyDailyBonusGameIconEnabled());
	}

	@When("Change location and verify lottery results")
	public void verifyLotteryResultsforDifferentLocation() {
		homeSteps.clickLotteryMenu();
		Assert.assertEquals("Modified location is not displayed", lotterySteps.changeLotteryLocation().toLowerCase(),
				lotterySteps.getLotteryCurrentLocation().toLowerCase());
	}

	@Then("Verify the last draw date and next draw date")
	public void verifyNextDrawingDate() throws ParseException {
		Assert.assertTrue("Last drawn date is not as current date", lotterySteps.verifyLastdrawCurrentdate());
		Assert.assertTrue("Next drawn date should be future date", lotterySteps.verifyNextdrawCurrentdate());
	}

	@Then("Verify next jackpot detail")
	public void verifyNextJackpotDetail() {
		Assert.assertTrue("Next Jackpot section is not displayed on lottery results.",
				lotterySteps.verifyNextJackpot());
	}
}