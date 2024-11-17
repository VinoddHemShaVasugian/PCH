package com.pch.automation.stepdefinitions.sw;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.jbehave.core.annotations.Then;
import com.pch.automation.steps.sw.OptinSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Contains Optin ligthbox Scenario Steps
 * 
 * @author mperumal
 *
 */
public class OptinScenarioSteps {

	@Steps
	OptinSteps optinSteps;

	/**
	 * Verify the display of Optin light box on home page
	 */
	@Then("Verify the presence of the Optin light box")
	public void verifyPresenceOfOptinLb() {
		assertTrue("Optin light box is failed to display", optinSteps.verifyOptinLb());
	}

	/**
	 * Verify the display of Optin light box on home page
	 */
	@Then("Verify the absence of the Optin light box")
	public void verifyAbsenceOfOptinLb() {
		assertFalse("Optin light box is display even though it has been checked when do registration",
				optinSteps.verifyOptinLb());
	}

	/**
	 * Verify the optinshowed db property value as '1'
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the dbproperty 'optinshowed' with value as '1'")
	public void verifyOptinDBProperty() throws IOException, SQLException {
		assertEquals("Optinshowed property failed to be as 1", 1, Integer.parseInt(optinSteps.verifyOptinDBProperty()));
	}

	/**
	 * Verify the absence of optinshowed db property
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the absence of the dbproperty 'optinshowed'")
	public void verifyAbsenceOfOptinDBProperty() throws IOException, SQLException {
		assertEquals("Optinshowed property should not be present for the user", "", optinSteps.verifyOptinDBProperty());
	}

	/**
	 * Verify the optinshowed expires db property value as '0'
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Then("Verify the dbproperty 'optinshowed' with expires value as '0'")
	public void verifyOptinExpireBProperty() throws IOException, SQLException {
		assertEquals("Optinshowed property failed to be as 0", 0,
				Integer.parseInt(optinSteps.verifyOptinExpireBProperty()));
	}
}
