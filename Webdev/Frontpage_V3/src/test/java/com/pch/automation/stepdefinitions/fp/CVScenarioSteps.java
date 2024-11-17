package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertEquals;

import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.fp.CVSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Consecutive Visits Scenario Steps
 * 
 * @author mperumal
 *
 */
public class CVScenarioSteps {

	@Steps
	CVSteps cvSteps;

	@Then("Verify the consecutive visit bar for the Day one")
	public void verifyCVBar() {
		assertEquals("Failed to make the user consecutive visit as Day one", 1, cvSteps.getCVDayCount());
	}

	@Then("Verify the token message and token amount on Token History page based on admin property '$AlternativeDescription', '$Tokens','$position'")
	public void verifyCVMessage(String descProperty, String tokenProperty, String position) {
		assertEquals("Consecutive visit message failed to display on Token History tab for " + position + " time",
				Integer.parseInt(position), cvSteps.verifyCVMsgFromTokenHistory(descProperty, tokenProperty, position));
	}
	
	
}
