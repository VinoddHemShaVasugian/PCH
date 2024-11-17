package com.pch.kenofrontend.steps;

import com.pch.kenofrontend.pages.HomePage;
import com.pch.kenofrontend.pages.ResultsPage;

import net.thucydides.core.annotations.Step;

public class ResultsPageSteps {

	ResultsPage resultsPage;
	HomePage homePage;

	@Step
	public void validateRecentDrawing() {
		resultsPage.get_recentdrawtime();
		resultsPage.getDrawnNumbersonResultsPage();
	}

	@Step
	public void validatePayTable() {
		resultsPage.paytable();
	}

	@Step
	public void dateDropdown() {
		resultsPage.datedropdown();
	}

	@Step
	public void calendar() {
		resultsPage.validateCalendar();
	}

	@Step
	public void pastdatedrawing() throws InterruptedException {
		resultsPage.waitforLiveDraw();
		resultsPage.pickpastdate();
	}

	@Step
	public void selectdrawingtime() {
		resultsPage.picktime();
	}

	@Step
	public void click_gobutton() {
		resultsPage.draw_GoButton();
	}

	@Step
	public void validate_livedraw() {
		resultsPage.validatematches();
	}
}
