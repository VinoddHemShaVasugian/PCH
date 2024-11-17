package com.pch.automation.steps.fp;

import java.text.ParseException;

import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.pages.fp.LotteryPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class LotterySteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HomePage homePage;
	LotteryPage lotteryPage;

	@Step
	public boolean claimTokens() {
		homePage.clickClaimButton();
		homePage.clickLotteryMenu();
		if (homePage.verifyClaimedButton()) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public String changeLotteryLocation() {
		return lotteryPage.changeLottryLocation();
	}

	@Step
	public String clickPastResults() {
		return lotteryPage.changeLottryLocation();
	}

	@Step
	public String clickPayout() {
		return lotteryPage.changeLottryLocation();
	}

	@Step
	public boolean verifyPastResults() {
		return lotteryPage.verifyPastResult();
	}

	@Step
	public boolean verifyPayout() {
		return lotteryPage.verifyPayout();
	}

	@Step
	public String getLotteryCurrentLocation() {
		return lotteryPage.getLotteryStateTitle().split(" ")[0];
	}

	@Step
	public boolean verifyLastdrawCurrentdate() throws ParseException {
		return lotteryPage.verifyLastDrawCurrentDate();
	}

	@Step
	public boolean verifyNextdrawCurrentdate() throws ParseException {
		return lotteryPage.verifyNextdrawCurrentDate();
	}

	@Step
	public boolean verifyNextJackpot() {
		return lotteryPage.verifyNextJackpot();
	}
}