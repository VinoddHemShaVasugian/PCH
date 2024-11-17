package com.pch.sw.tests.guidedSearch;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class GuidedSearchPublishUpAndDown extends BaseTest {

	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	GuidedSearchPage gsPage;
	User randomUser_1;

	@Test(groups={GroupNames.Mobile}, description = "Validating Publish Up and Down date - 29932")
	public void validatePublishUpandDown() {

		// Validating for valid Publish up and Down scenario
		CustomLogger
				.log("############### validating Publish Up and Down functionality for a valid scenario ###############");
		homePage.loadGuidedSearch("SWGSVamsiL2_Enabled");
		Assert.assertTrue(gsPage.validateGSURL("SWGSVamsiL2_Enabled"),
				"you are not in expected GS ");
		CustomLogger
				.log("#################################################################################################");

		// Validating for invalid Publish up and Down scenario
		CustomLogger
				.log("############### validating Publish Up and Down functionality for a invalid scenario ###############");
		homePage.loadGuidedSearch("Autoamtion_Invalid_publish_Date_L1_3");
		Assert.assertFalse(gsPage.validateGSURL("Autoamtion_Invalid_publish_Date_L1_3"),
				"you are on the GS which u set it GS-admin, but you should not be ");
		CustomLogger
				.log("###################################################################################################");

	}
}
