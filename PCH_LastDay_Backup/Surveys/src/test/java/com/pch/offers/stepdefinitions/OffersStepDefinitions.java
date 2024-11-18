package com.pch.offers.stepdefinitions;

import com.pch.offers.offersadmin.OffersPages;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.When;

public class OffersStepDefinitions {

	private static String offersAdminUrl = ConfigurationReader.getOffersAdminUrl();
	private String offersAdminUserName = ConfigurationReader.getOffersAdminUserName();
	private String offersAdminPassword = ConfigurationReader.getOffersAdminPassword();

	private static OffersPages offersPages = new OffersPages();

	@When("I login to offers admin")
	public void userLoginToOffersAdmin() {
		WebdriverBuilder.getDriver().navigate().to(offersAdminUrl);
		if (WebdriverBuilder.getDriver().getCurrentUrl().contains("login")) {
			offersPages.loginOffersAdmin(offersAdminUserName, offersAdminPassword);
		}
	}
}