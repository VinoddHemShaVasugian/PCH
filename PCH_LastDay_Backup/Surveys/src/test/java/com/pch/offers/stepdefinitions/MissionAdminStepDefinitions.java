package com.pch.offers.stepdefinitions;

import org.junit.Assert;

import com.pch.offers.offersadmin.MissionAdminPages;
import com.pch.survey.centralservices.Registrations;
import com.pch.survey.webdrivers.WebdriverBuilder;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MissionAdminStepDefinitions {

	private MissionAdminPages missionAdminPage = new MissionAdminPages(WebdriverBuilder.getDriver());;

	@When("I navigates to Mission Amazon Gift Cards page")
	public void whenNavigatesToAmzGcPage() {
		missionAdminPage.clickAmzGiftCardMenu();
		missionAdminPage.searchAmzGcRecord(Registrations.getGmt());
		missionAdminPage.retriveAmzGiftCardColumns();
	}

	@Then("I verify the GMT of gift card")
	public void thenVerifyGiftCardGmt() {
		Assert.assertEquals("Gift card details not available in admin recods for gmt: " + Registrations.getGmt(),
				Registrations.getGmt(), missionAdminPage.getGMTofAmzGiftcard());
	}

	@Then("I verify the transaction status of gift card {string}")
	public void thenVerifyGiftCardEmailProcessingStatus(String transactionStatus) {
		Assert.assertEquals("Transaction status mismatched for Gift card details", transactionStatus,
				missionAdminPage.getTransactionStatusofAmzGiftcard());
	}

	@Then("I verify the amount of gift card {string}")
	public void thenVerifyGiftCardAmount(String amount) {
		Assert.assertEquals("Gift card amount mismatched", amount, missionAdminPage.getAmountofAmzGiftcard());
	}
}