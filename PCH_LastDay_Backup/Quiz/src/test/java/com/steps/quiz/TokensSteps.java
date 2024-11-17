package com.steps.quiz;

import java.io.IOException;
import java.sql.SQLException;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.LightboxPage;
import com.pages.quiz.MyAccountPage;
import com.pages.quiz.RegistrationPage;
import com.pages.quiz.SignInPage;
import com.pch.quiz.database.DatabaseHelper;
import com.pch.quiz.utilities.AppConfigLoader;
import com.pch.quiz.utilities.WebServiceClient;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class TokensSteps extends ScenarioSteps {
	private static final long serialVersionUID = 1L;

	/** To access My Account page. */
	HeaderAndUninavPage headerAndUninavPage;
	MyAccountPage accountPage;
	SignInPage signInPage;
	LightboxPage lbPage;
	RegistrationPage regPage;
	WebServiceClient webClient = WebServiceClient.getInstance();
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	@Step
	public int verifyTokenTransactionsDetails(String description, String tokens, int position) {
		String descKey = description;
		String tokenKey = tokens;
		if (AdminSteps.getArticleDetails().get(description) != null) {
			descKey = AdminSteps.getArticleDetails().get(description);
		}
		if (AdminSteps.getArticleDetails().get(tokens) != null) {
			tokenKey = AdminSteps.getArticleDetails().get(tokens);
		} else if (GOSSteps.getGosDetails().get(tokens) != null) {
			tokenKey = GOSSteps.getGosDetails().get(tokens);
		}
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
	public void completeSilverReg() throws Exception {
		lbPage.completeSilverUser();
		headerAndUninavPage.verifySignout();
	}

	@Step
	public void signinWithLastCreatedUser() {
		headerAndUninavPage.clickSignIn();
		signInPage.login(RegistrationPage.regGenerator.getEmail(), RegistrationPage.userPassword);
		headerAndUninavPage.verifySignout();
	}
}