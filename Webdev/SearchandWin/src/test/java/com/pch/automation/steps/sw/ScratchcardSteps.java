package com.pch.automation.steps.sw;

import java.io.IOException;
import java.sql.SQLException;

import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.ScratchcardPage;
import com.pch.automation.steps.NavigationSteps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class ScratchcardSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	LightboxPage lbPage;
	ScratchcardPage scratchCardPage;
	NavigationSteps navigationSteps;
	MyAccount accountPage;

	/**
	 * Return the page title
	 * 
	 */
	@Step
	public String pageTitle() throws IOException, SQLException {
		return getDriver().getTitle();
	}

	@Step
	public Boolean verifyScratchcardLB() throws IOException, SQLException {
		return lbPage.verifyScratchcardLightbox();
	}

	@Step
	public void closeScratchcardLB() throws IOException, SQLException {
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
}