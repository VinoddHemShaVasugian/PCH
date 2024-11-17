package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.TokensSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Consecutive Visits Scenario Steps
 * 
 * @author mperumal
 *
 */
public class TokenQueueSteps {

	@Steps
	TokensSteps tokenSteps;

	@Then("Verify the token queue record details with status as '$status'")
	public void verifyTokenQueueRecord(String status) throws IOException, SQLException {
		assertEquals("Status is mismatched for the expected token queue record", Integer.parseInt(status),
				tokenSteps.verifyTokenQueueRecord(status));
	}

	@Then("Verify the absence of the token queue record")
	public void verifyAbsenceOfTokenQueueRecord() throws IOException, SQLException {
		assertEquals("Token queue records are still present in table after run the cron helper url", 0,
				tokenSteps.getTokenQueueRecordCount());
	}

	@Then("Verify the tokens are awarded from the queue")
	public void verifyTokenQueueRecordOnApplication() {
		tokenSteps.signinWithLastCreatedUser();
		assertTrue("Queued tokens are failed to award to the user", tokenSteps.getExtensionTokenAmount() > 0);
	}

}
