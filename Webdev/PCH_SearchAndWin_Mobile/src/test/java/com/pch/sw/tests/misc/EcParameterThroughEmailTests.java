package com.pch.sw.tests.misc;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.User;

/*
 * EC Parameters through email tests
*/
public class EcParameterThroughEmailTests extends BaseTest {

	private HomePage homePage;
	private WebHeaderPage headerPage;
	RegistrationPage regPage;
	private User randomUser_1;

	@Test(description = "Test Case ID : 23338")
	public void testEntriesComfirmed() {

		homePage.load();
		loginToSearch(randomUser_1);

		// Reload the page with ec=1 in the url and validate the entry message
		headerPage.addEcParameterAndReloadPage("1");
		String app_message = headerPage.getmsg();
		// String latestActivity = headerPage.getLatestActivityFromStatus();
		Assert.assertTrue(app_message.contains("Your entry has been confirmed!"));

		// Reload the page with ec=5 in the url and validate the entry message
		headerPage.addEcParameterAndReloadPage("5");
		app_message = headerPage.getmsg();
		// latestActivity = headerPage.getLatestActivityFromStatus();
		Assert.assertTrue(app_message.contains("Your entries have been confirmed!"));
	}
}
