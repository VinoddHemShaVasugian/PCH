package com.pch.automation.steps;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SignInPage;
import com.pch.automation.steps.jm.FpAdminSteps;
import com.pch.automation.utilities.AppConfigLoader;
import com.pch.automation.utilities.WebServiceClient;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class TokensSteps extends ScenarioSteps {
	private static final long serialVersionUID = 1L;

	/** To access My Account page. */
	HeaderAndUninavPage headerAndUninavPage;
	MyAccount accountPage;
	SignInPage signInPage;
	LightboxPage lbPage;
	RegistrationPage regPage;
	WebServiceClient webClient = WebServiceClient.getInstance();
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	@Step
	public int verifyTokenTransactionsDetails(String description, String tokens, int position) {
		String descKey = FpAdminSteps.getArticleDetails().get(description);
		String tokenKey = FpAdminSteps.getArticleDetails().get(tokens);
		return accountPage.verifyTokenTransactionsDetails(descKey, tokenKey, position);
	}

	/**
	 * Return the DB Property of Extension Tokens
	 * 
	 * @param webClient
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	@Step
	public String verifyExtensionTokensDBProperty(String dbProperty) throws IOException, SQLException {
		String userId = webClient.getUserIdFromEmail(RegistrationPage.regGenerator.getEmail());
		String query = "select value from sso_user_data where user = '" + userId + "' and item = '" + dbProperty + "';";
		String prop = dbHelper.executeQuery(query);
		return prop.substring(prop.indexOf("Status") + 10, prop.length() - 2);
	}

	/**
	 * Formulate the VIP message based on the property key.
	 * 
	 * @param msgTypeHeader
	 * @param msgTypeBody
	 * @param firstName
	 * @return
	 * @throws Exception
	 */
	@Step
	public String formulateExpectedVipMessage(String msgTypeHeader, String msgTypeBody, String firstName)
			throws Exception {
		return configInstance.msgPropertyFileReader(msgTypeHeader, firstName) + "\n"
				+ configInstance.msgPropertyFileReader(msgTypeBody);
	}

	@Step
	public int getExtensionTokenAmount() {
		return headerAndUninavPage.getTokens();
	}

	@Step
	public boolean verifyCompleteRegLb() throws Exception {
		return lbPage.verifyCompleteRegistrationLightbox();
	}

	@Step
	public boolean verifySilverCompleteRegLb() throws Exception {
		return lbPage.verifySilverCompleteRegistrationLightbox();
	}

	@Step
	public void closeCompleteRegLb() throws Exception {
		lbPage.closeCompleteRegistrationLightbox();
	}

	@Step
	public void completeMiniReg() throws Exception {
		lbPage.clickCompleteRegContinueBtn();
		signInPage.login(RegistrationPage.userPassword);
		regPage.completeMiniRegUser();
	}

	@Step
	public void completeSocilaReg() throws Exception {
		lbPage.clickCompleteRegContinueBtn();
		regPage.compelteSocialUser();
	}

	@Step
	public void completeSilverReg() throws Exception {
		lbPage.completeSilverUser();
		headerAndUninavPage.verifySignout();
	}

	@Step
	public int verifyTokenQueueRecord(String status) throws IOException, SQLException {
		String gmt = RegistrationPage.regGenerator.getGmt();
		String query = "select request_data,status from tokens_credit_queue;";
		LinkedList<LinkedHashMap<String, String>> resultSet = dbHelper.getMulitpleRowsAndColumnValues(query);
		for (LinkedHashMap<String, String> map : resultSet) {
			String requestGmt = WebServiceClient.getInstance().jsonParser(map.get("request_data"), "gmt");
			if (requestGmt != null && requestGmt.equalsIgnoreCase(gmt)) {
				return Integer.parseInt(map.get("status"));
			}
		}
		return -1;
	}

	@Step
	public int getTokenQueueRecordCount() throws IOException, SQLException {
		String query = "select count(*) from tokens_credit_queue;";
		return Integer.parseInt(dbHelper.executeQuery(query));
	}

	@Step
	public void signinWithLastCreatedUser() {
		headerAndUninavPage.clickSignIn();
		signInPage.login(RegistrationPage.regGenerator.getEmail(), RegistrationPage.userPassword);
		headerAndUninavPage.verifySignout();
	}
}