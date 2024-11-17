package com.miscellaneous;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class EcParameterTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final String single_entry_message = "Your entry has been confirmed";
	private final String mulitple_entry_message = "Your entries has been confirmed";

	@testId(test_id = "RT-04224")
	@testCaseName(test_case_name = "[D/T/M] Frontpage- Pass EC parameter through email")
	@Test(priority = 1, description = "Verify the Entry message in the Latest activity tab by passing Ec parameter in URL", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04224:[D/T/M] Frontpage- Pass EC parameter through email")
	public void verify_entry_message_on_latest_activity_tab() throws Exception {
		test_Method_details(1, "Verify the Entry message in the Latest activity tab by passing Ec parameter in URL");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Entry message by adding parameter ec avalue as 1");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "?ec=1");
		assertEquals(homepage_instance.get_latest_entry_activity_message(), single_entry_message);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Entry message by adding parameter ec avalue as greater than 1");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "?ec=4");
		assertEquals(homepage_instance.get_latest_entry_activity_message(), mulitple_entry_message);
		step_validator(3, true);
	}

	@testId(test_id = "RT-04224")
	@testCaseName(test_case_name = "[D/T/M] Frontpage- Pass EC parameter through email")
	@Test(priority = 2, description = "Verify the Promotion campaign email's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04224:[D/T/M] Frontpage- Pass EC parameter through email")
	public void verify_promotion_campaign_email() throws Exception {
		test_Method_details(2, "Verify the Promotion campaign email's");
		// Step 1
		test_step_details(1, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createGoldUser();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Append the Promotion campaign details to the user and verify it");
		invokeBrowser(user_details[1]
				+ "&utm_source=email&utm_medium=front_fpemail&utm_campaign=16FP5201&tsrc=front_fpemail&tsrc2=16FP5201&mailid=16FP5201&promo-key=16FP5201&edid=exp79117&tp=i-H8B-8l-Ka5-6ehczc-1c-1C3pl-1c-G-6ebhd6-JK85L&submeta=FrontPage");
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		homepage_instance.search(generateRandomString(6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		switchToMainTab();
		doRefresh();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.get_Tokens() > 0);
		step_validator(2, true);
	}
}
