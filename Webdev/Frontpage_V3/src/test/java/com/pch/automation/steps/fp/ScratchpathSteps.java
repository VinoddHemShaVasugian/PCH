package com.pch.automation.steps.fp;

import java.io.IOException;
import java.sql.SQLException;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.ScratchcardPage;
import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.steps.NavigationSteps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class ScratchpathSteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	LightboxPage lbPage;
	ScratchcardPage scratchCardPage;
	NavigationSteps navigationSteps;
	MyAccount accountPage;
	HomePage homePage;
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();

	/**
	 * Return the page title
	 * 
	 */
	@Step
	public String pageTitle() {
		waitABit(5000);
		return getDriver().getTitle();
	}

	@Step
	public boolean verifyScratchcardLB() {
		return lbPage.verifyScratchcardLightbox();
	}

	@Step
	public void closeScratchcardLB() {
		lbPage.closeScratchCardLightbox();
	}

	@Step
	public int getScratchPathTotalGameCount() {
		return Integer.parseInt(scratchCardPage.getScratchPathTotalGameCount());
	}

	@Step
	public void waitForScratchPathAdsToComplete() throws Exception {
		scratchCardPage.waitForScratchPathAdsToComplete();
	}

	@Step
	public String playScratchPathGame() throws Exception {
		return scratchCardPage.playScratchPathGame();
	}

	@Step
	public String getTokenTransactionAmount(int i) {
		return accountPage.getTokenTransactionAmount(i);
	}

	@Step
	public boolean verifyBGInfoWindow() {
		return homePage.verifyDailyBonusGameInfoWindow();
	}

	@Step
	public void clickBGInfoWindow() {
		homePage.clickDailyBonusGameInfoIcon();
	}

	@Step
	public void closeBGInfoWindow() {
		homePage.closeDailyBonusGameInfoWindow();
	}

	@Step
	public int getDailyBonusGameProgressLevel() {
		return homePage.getDailyBonusGameCheckCount();
	}

	@Step
	public boolean verifyBGIcon() {
		return homePage.verifyDailyBonusGamePlayIcon();
	}

	@Step
	public void clickPlayBGicon() {
		homePage.clickDailyBonusGamePlayIcon();
	}

	@Step
	public int getDailyProgressMissionDetails() throws IOException, SQLException {
		return Integer.parseInt(dbHelper.getDailyProgressMissionDetails(RegistrationPage.regGenerator.getEmail()));
	}

	@Step
	public boolean verifyHome() {
		return homePage.verifyHomePage();

	}
}