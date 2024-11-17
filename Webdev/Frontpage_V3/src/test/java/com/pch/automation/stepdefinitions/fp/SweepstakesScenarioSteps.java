package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.fp.SweepstakeSteps;
import com.pch.automation.steps.jm.FpAdminSteps;

import net.thucydides.core.annotations.Steps;

public class SweepstakesScenarioSteps {

	@Steps
	SweepstakeSteps sweepSteps;
	@Steps
	HomepageSteps homeSteps;

	@Then("Verify the application redirected to EDL home page")
	public void verifyEDLHomePage() {
		assertTrue("Failed to redirect EDL homepage.", sweepSteps.verifyEDLHomePage());
	}

	@Then("Verify the sweepstakes '$article','$sweepsDesc'")
	public void verifySweepsDescription(String article, String sweepsDesc) {
		assertTrue("Sweepstakes is not displayed on " + article, sweepSteps.verifySweepstakes());
		assertEquals("Sweepstakes description is mismatch on " + article,
				FpAdminSteps.getArticleDetails().get(article + sweepsDesc), sweepSteps.getSweepsDescription());
	}

	@Then("Play sweeps and verify the sweepstakes path '$article','$sweepsPath'")
	public void playSweepsAndVerifySweepsPath(String article, String sweepsPath) {
		sweepSteps.clickSweeps();
		assertTrue("Sweepstakes path is mismatch for " + article,
				sweepSteps.verifySweepsPath(FpAdminSteps.getArticleDetails().get(article + sweepsPath)));
	}

	@Then("Launch sweeps path return URL and verify sweeps activity '$article','$crmn'")
	public void completeSweeps(String article, String crmn) {
		sweepSteps.navigateToCRMN(FpAdminSteps.getArticleDetails().get(article + crmn));
		assertTrue("Sweeps progress is not displayed", sweepSteps.verifySweepsProgress());
		assertTrue("Sweeps entry is mismatched", sweepSteps.verifySweepsEntryActivity());
		assertEquals("latest entry activity message is mismatch", sweepSteps.readSweepsEntryComfirmationMsg().trim(),
				homeSteps.getLatestActivityMessage().trim());
	}

	@Then("Verify sweeps complete status and success message '$article','$sweepsCompleteMsg'")
	public void verifySweepsCompleteMsg(String article, String sweepsCompleteMsg) {
		assertEquals("Sweepstakes completion message is mismatch",
				FpAdminSteps.getArticleDetails().get(article + sweepsCompleteMsg),
				sweepSteps.getSweepsCommpletedStatusMessage());
	}

	@Then("Verify sweeps property on db '$article','$crmn'")
	public void verifySweepsPropertyOnDB(String article, String crmn) throws IOException, SQLException {
		assertEquals("Sweepsstakes property is not updated in db.",
				FpAdminSteps.getArticleDetails().get(article + crmn), sweepSteps.getSweepsPropertyFromDB());
	}
}
