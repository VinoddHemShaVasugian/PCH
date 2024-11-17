package com.pch.automation.steps.fp;

import java.io.IOException;
import java.sql.SQLException;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.utilities.WebServiceClient;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class OptinSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	LightboxPage lbPage;
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();
	WebServiceClient webClient = WebServiceClient.getInstance();
	RegistrationPage regPage;

	/**
	 * Verify the Optin light box
	 * 
	 * @returnF
	 */
	@Step
	public boolean verifyOptinLb() {
		return lbPage.verifyOptinLightbox();
	}

	/**
	 * Close the Optin light box
	 */
	@Step
	public void closeOptinLb() {
		lbPage.closeOptinLightbox();
	}

	/**
	 * Return the DB Property of Optin Light box
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Step
	public String verifyOptinDBProperty() throws IOException, SQLException {
		String userId = webClient.getUserIdFromEmail(RegistrationPage.userEmail);
		String query = "select value from sso_user_data where user = '" + userId + "' and item like 'optinshowed';";
		return dbHelper.executeQuery(query);
	}

	/**
	 * Return the Expire DB Property of Optin Light box
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Step
	public String verifyOptinExpireBProperty() throws IOException, SQLException {
		String userId = webClient.getUserIdFromEmail(RegistrationPage.userEmail);
		String query = "select expires from sso_user_data where user = '" + userId + "' and item like 'optinshowed';";
		return dbHelper.executeQuery(query);
	}

	@Step
	public boolean verifyGuestLb() {
		return lbPage.verifyGuestuserLB();
	}

	/**
	 * Close the Optin light box
	 */
	@Step
	public void closeGuestOptinLb() {
		lbPage.closeGuestUserLb();
	}

	@Step
    public void guestuserlightbox()

{
		lbPage.verifyGuestUserLB();
}

}
