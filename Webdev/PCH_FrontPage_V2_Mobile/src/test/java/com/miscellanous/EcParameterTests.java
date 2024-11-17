package com.miscellanous;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class EcParameterTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();

	@testId(test_id = "26353,26297")
	@testCaseName(test_case_name = "B-27981 Frontpage- Pass EC parameter through email,B-27981 Frontpage- Pass EC parameter through email")
	@Test(priority = 1, description = "Verify the Entry message by passing Ec parameter in URL", groups = { "DESKTOP",
			"TABLET" }, testName = "26353:B-27981 Frontpage- Pass EC parameter through email,26297:B-27981 Frontpage- Pass EC parameter through email")
	public void verify_entry_message_for_ec_parameter() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the Entry message by adding parameter ec avalue as 1");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "?ec=1");
		assertEquals(homepage_instance.get_unis_message(), msg_property_file_reader("single_entry_message"));

		// Step 3
		test_step_details(3, "Verify the Entry message by adding parameter ec avalue as greater than 1");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "?ec=4");
		assertEquals(homepage_instance.get_unis_message(), msg_property_file_reader("mulitple_entry_message"));
	}
}
