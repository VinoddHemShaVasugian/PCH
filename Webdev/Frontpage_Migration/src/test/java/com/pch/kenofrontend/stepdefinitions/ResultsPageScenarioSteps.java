package com.pch.kenofrontend.stepdefinitions;

import net.thucydides.core.annotations.Steps;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import com.pch.kenofrontend.steps.ResultsPageSteps;

public class ResultsPageScenarioSteps {

	@Steps
	ResultsPageSteps resultpageSteps;

	@Then("It should display default latest drawing result on the page")
	public void latestDrawing() {
		resultpageSteps.validateRecentDrawing();

	}

	@Then("Pay Table should show correctly on click")
	public void paytable() {
		resultpageSteps.validatePayTable();
	}

	@When("user clicks on Date drop down")
	public void clickdateDropdown() {
		resultpageSteps.dateDropdown();
	}

	@Then("it should display the Calendar for today and past 13 days. Dates before 14 days must not be enabled")
	public void validatecalendarfor_14days() {
		resultpageSteps.calendar();
	}

	@Then("user selects past date from date drop down")
	public void selectpastdatedrawingfortokentime() throws InterruptedException {
		resultpageSteps.pastdatedrawing();
	}

	@Then("selects drawing time from time drop down")
	public void selectdrawingtime() {
		resultpageSteps.selectdrawingtime();
	}

	@Then("click on Go button")
	public void drawgobutton() {
		resultpageSteps.click_gobutton();
	}

	@Then("it should show drawing numbers for that specific drawing along with it's relative Pay Table under Winning numbers from Token Drawing section")
	public void validate_matches() {
		resultpageSteps.validate_livedraw();
	}
}
