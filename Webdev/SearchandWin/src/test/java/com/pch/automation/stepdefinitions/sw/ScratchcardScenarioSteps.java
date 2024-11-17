package com.pch.automation.stepdefinitions.sw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.cs.SegmentationSteps;
import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.steps.sw.ScratchcardSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains ScratchPath Scenario Steps
 * 
 * @author mperumal
 *
 */
public class ScratchcardScenarioSteps {

	@Steps
	ScratchcardSteps scratchcardSteps;
	@Steps
	SegmentationSteps segSteps;
	@Steps
	NavigationSteps navSteps;

	/**
	 * Assign the scratch card segment to the user
	 */
	@Given("Assign the user to the segment based on the '$Segments' admin property")
	@When("Assign the user to the segment based on the '$Segments' admin property")
	public void assignSegment(String segmentName) {
		segSteps.assignSegmentByName(SearchAdminSteps.getArticleDetails().get(segmentName));
	}

	/**
	 * Login to the Search Win application with the created RF user details
	 */

	@Then("Login to the Search application with the created user")
	public void loginToSWApplication() {
		String queryParam = "email=" + RegistrationPage.regGenerator.getEmail() + "&e="
				+ RegistrationPage.regGenerator.getGmt();
		navSteps.navigateToSWApplication(queryParam);
	}

	/**
	 * Verify the Scratchcard page redirection by its title
	 * 
	 */
	@Then("Verify the application redirected to Scratchcard page")
	public void verifyScratchpathPage() throws IOException, SQLException {
		assertEquals("Failed to redirect to Scratchcard page", "Scratch Path", scratchcardSteps.pageTitle());
	}

	/**
	 * Verify the presence of the Scratchcard light box
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the presence of the Scratchcard light box")
	public void verifyAbsenceOfScratchcardLB() throws IOException, SQLException {
		assertTrue("Failed to display to Scratchcard light box", scratchcardSteps.verifyScratchcardLB());
		scratchcardSteps.closeScratchcardLB();
	}

	/**
	 * Login to the Search Win application with the created RF user details
	 */
	@Then("Login to the Search application with blocked segid from property '$Conditions'")
	public void loginToSWApplicationWithSegid(String condition) {
		String segId = SearchAdminSteps.getArticleDetails().get(condition).split(" ")[1];
		segId = segId.substring(1, segId.length());
		String queryParam = "email=" + RegistrationPage.regGenerator.getEmail() + "&e="
				+ RegistrationPage.regGenerator.getGmt() + "&" + segId;
		navSteps.navigateToSWApplication(queryParam);
	}

	@Then("Verify the application redirected to homepage instead of scratchcard page")
	public void verifyHomepagePage() throws SQLException, IOException {
		assertEquals("Failed to redirect to Home page", "Search & Win", scratchcardSteps.pageTitle());
	}

	@Then("Play and verify scratchpath games")
	public void playScratchpathGames() throws Exception {
		String scratchPathToken;
		int totalGameCount = scratchcardSteps.getScratchPathTotalGameCount();
		for (int count = 0; count < totalGameCount; count++) {
			scratchcardSteps.waitForScratchPathAdsToComplete();
			scratchPathToken = scratchcardSteps.playScratchPathGame();
			scratchcardSteps.waitForScratchPathAdsToComplete();
			navSteps.redirectToTokenHistoryPage();
			if (count < totalGameCount - 1) {
				assertEquals("Token amount didnot match", scratchcardSteps.getTokenTransactionAmount(1),
						scratchPathToken);
				navSteps.navigateToSWApplication();
			}
		}
	}
}
